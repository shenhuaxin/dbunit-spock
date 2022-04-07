package com.kylle.test

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import groovy.xml.MarkupBuilder
import org.dbunit.dataset.IDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.ext.mysql.MySqlConnection
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

        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new ByteInputStream(dataSetStr.getBytes(), dataSetStr.getBytes().length))
        MySqlConnection connection = new MySqlConnection(DataSourceHolder.getConnection(), dbunit.schema() == "" ? null : dbunit.schema())
        connection.getConnection().setAutoCommit(false)
        DatabaseOperationLookup.get(dbunit.operation()).execute(connection, dataSet)
        try {
            invocation.proceed()
        } finally {
            if (dbunit.rollback()) {
                connection.getConnection().rollback()
            } else {
                connection.getConnection().commit()
            }
        }
    }


    private static String writeXmlDataSet(Closure dataSetClosure) {
        def xmlWriter = new StringWriter()
        def builder = new MarkupBuilder(xmlWriter)
        builder.dataset(dataSetClosure)
        return xmlWriter as String
    }
}
