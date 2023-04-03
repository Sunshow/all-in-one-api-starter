package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.example.gradle.plugin.generator.util.className
import com.squareup.javapoet.AnnotationSpec
import com.squareup.javapoet.FieldSpec
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import com.thoughtworks.qdox.model.JavaClass
import com.thoughtworks.qdox.model.JavaField
import java.io.File
import javax.lang.model.element.Modifier

object GenerateRepositoryEntityJob {

    fun generate(config: GenerateConfig, pojoClass: JavaClass, pojoFieldList: List<JavaField>) {
        val tableAnnotationSpec =
            AnnotationSpec.builder(ClassNames.JpaTable)
                .addMember("name", "\$S", config.tableName)
                .build()
        val equalsAndHashCodeAnnotationSpec =
            AnnotationSpec.builder(ClassNames.LombokEqualsAndHashCode)
                .addMember("callSuper", "true")
                .build()
        val typeSpecBuilder = TypeSpec.classBuilder(config.repositoryEntityName)
            .addModifiers(Modifier.PUBLIC)
            .superclass(
                if (config.softDelete) {
                    ClassNames.SoftDeleteQEntity
                } else {
                    ClassNames.QEntity
                }
            )
            .addAnnotation(equalsAndHashCodeAnnotationSpec)
            .addAnnotation(ClassNames.LombokData)
            .addAnnotation(tableAnnotationSpec)
            .addAnnotation(ClassNames.JpaEntity)
            .addAnnotation(ClassNames.HibernateDynamicInsert)
            .addAnnotation(ClassNames.HibernateDynamicUpdate)

        if (!pojoClass.comment.isNullOrEmpty()) {
            typeSpecBuilder.addJavadoc(pojoClass.comment)
        }

        for (javaField in pojoFieldList) {
            val fieldName = javaField.name
            // 忽略不处理父类已处理的保留字段
            if (fieldName == "id" || fieldName == "createdTime" || fieldName == "updatedTime") {
                continue
            }

            val fieldType = javaField.type

            // 添加字段
            val builder = FieldSpec.builder(fieldType.className(), fieldName, Modifier.PRIVATE)
            typeSpecBuilder.addField(builder.build())
        }

        val javaFile =
            JavaFile.builder(config.repositoryEntityPackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.providerJavaOutputDir))
    }

}