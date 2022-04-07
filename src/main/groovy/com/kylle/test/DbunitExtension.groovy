package com.kylle.test


import org.spockframework.runtime.extension.IAnnotationDrivenExtension
import org.spockframework.runtime.model.FeatureInfo

class DbunitExtension implements IAnnotationDrivenExtension<Dbunit> {

    @Override
    void visitFeatureAnnotation(Dbunit dbunit, FeatureInfo feature) {

        feature.featureMethod.addInterceptor(new DbUnitInterceptor(dbunit, feature))
    }



}
