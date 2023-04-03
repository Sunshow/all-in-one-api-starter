package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeSpec
import java.io.File
import javax.lang.model.element.Modifier

object GenerateRepositoryJob {

    fun generate(config: GenerateConfig) {
        val baseRepositoryTypeName = ParameterizedTypeName.get(
            ClassNames.QRepository,
            config.repositoryEntityClassName,
            ClassNames.JavaLong
        )

        val typeSpecBuilder = TypeSpec.interfaceBuilder(config.repositoryName)
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(baseRepositoryTypeName)


        val javaFile =
            JavaFile.builder(config.repositoryPackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.providerJavaOutputDir))
    }

}