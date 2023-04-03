package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec
import java.io.File
import javax.lang.model.element.Modifier

object GenerateServiceImplJob {

    fun generate(config: GenerateConfig) {
        val baseServiceImplTypeName = ParameterizedTypeName.get(
            ClassNames.DefaultQServiceImpl,
            config.pojoClassName,
            ClassNames.JavaLong,
            config.repositoryEntityClassName,
            config.repositoryClassName
        )

        val typeSpecBuilder = TypeSpec.classBuilder(config.serviceImplName)
            .addSuperinterface(config.serviceClassName)
            .superclass(baseServiceImplTypeName)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(ClassNames.LombokSlf4j)
            .addAnnotation(ClassNames.LombokRequiredArgsConstructor)
            .addAnnotation(ClassNames.EnableSoftDelete)
            .addAnnotation(ClassNames.SpringService)

        val javaFile =
            JavaFile.builder(config.serviceImplPackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.providerJavaOutputDir))
    }

}