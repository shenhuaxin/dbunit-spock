package com.kylle.test

import groovy.xml.MarkupBuilder
import org.dbunit.database.IDatabaseConnection
import org.dbunit.dataset.ReplacementDataSet
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder
import org.dbunit.ext.h2.H2Connection
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


        def builder = new FlatXmlDataSetBuilder()
        builder.setColumnSensing(true)
        ReplacementDataSet replacementDataSet = new ReplacementDataSet(builder.build(new ByteArrayInputStream(dataSetStr.getBytes())))
        replacementDataSet.addReplacementObject("[null]", null)
        replacementDataSet.addReplacementObject("[NULL]", null)
        IDatabaseConnection connection = new H2Connection(DataSourceHolder.getConnection(), dbunit.schema() == "" ? null : dbunit.schema())
        DatabaseOperationLookup.get(dbunit.operation()).execute(connection, replacementDataSet)
        invocation.proceed()
    }


    private static String writeXmlDataSet(Closure dataSetClosure) {
        def xmlWriter = new StringWriter()
        def builder = new MarkupBuilder(xmlWriter)
        builder.dataset(dataSetClosure)
        return xmlWriter as String
    }
}
