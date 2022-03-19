package com.kylle.test


import groovy.xml.MarkupBuilder
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.ext.mysql.MySqlConnection
import org.dbunit.operation.DatabaseOperation
import org.spockframework.runtime.extension.AbstractMethodInterceptor
import org.spockframework.runtime.extension.IMethodInvocation
import org.spockframework.runtime.model.FeatureInfo

class DbUnitInterceptor extends AbstractMethodInterceptor {

    Dbunit dbunit;
    FeatureInfo feature;

    DbUnitInterceptor(Dbunit dbunit, FeatureInfo feature) {
        this.dbunit = dbunit;
        this.feature = feature;
    }

    @Override
    void interceptFeatureMethod(IMethodInvocation invocation) throws Throwable {


        Closure closure = dbunit.content().newInstance(invocation.instance, invocation.instance)
        String dataSetStr = writeXmlDataSet(closure)

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new ByteArrayInputStream(dataSetStr.getBytes()))
        MySqlConnection connection = new MySqlConnection(DataSourceHolder.getConnection(), dbunit.schema() == "" ? null : dbunit.schema())
        DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet)

        invocation.proceed()
    }


    private static String writeXmlDataSet(Closure dataSetClosure) {
        def xmlWriter = new StringWriter()
        def builder = new MarkupBuilder(xmlWriter)
        builder.dataset(dataSetClosure)
        return xmlWriter as String
    }
}
