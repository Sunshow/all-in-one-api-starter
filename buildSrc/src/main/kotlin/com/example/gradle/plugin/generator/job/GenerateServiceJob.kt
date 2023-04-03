package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec
import java.io.File
import javax.lang.model.element.Modifier

object GenerateServiceJob {

    fun generate(config: GenerateConfig) {
        val baseServiceTypeName =
            ParameterizedTypeName.get(ClassNames.BaseQService, config.pojoClassName, ClassNames.JavaLong)

        val typeSpecBuilder = TypeSpec.interfaceBuilder(config.serviceName)
            .addSuperinterface(baseServiceTypeName)
            .addModifiers(Modifier.PUBLIC)

        val javaFile =
            JavaFile.builder(config.servicePackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.domainJavaOutputDir))
    }

}