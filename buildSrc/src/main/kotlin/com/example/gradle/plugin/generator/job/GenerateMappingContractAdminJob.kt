package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.javapoet.KotlinPoetJavaPoetPreview
import com.squareup.kotlinpoet.javapoet.toKClassName
import com.thoughtworks.qdox.model.JavaClass
import java.io.File

/**
 * 生成 kotlin 版本的自动映射合约代码
 */
object GenerateMappingContractAdminJob {

    @OptIn(KotlinPoetJavaPoetPreview::class)
    fun generate(config: GenerateConfig, pojoClass: JavaClass) {
        val file = FileSpec.builder(config.adminContractPackage, config.adminContractName)
            .addType(
                TypeSpec.interfaceBuilder(config.adminContractName)
                    .addAnnotation(ClassNames.AdminSessionScope.toKClassName())
                    .addAnnotation(
                        AnnotationSpec.builder(ClassNames.AutoMappingContract.toKClassName())
                            .addMember("paths = [%S]", "/admin/${config.pojoBIModule}")
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("search")
                            .addModifiers(KModifier.ABSTRACT)
                            .addKdoc("查找${pojoClass.comment}列表")
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AdminPermissionScope.toKClassName())
                                    .addMember("%S", "${config.adminResourcePrefix}list")
                                    .build()
                            )
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AutoMappingContract.toKClassName())
                                    .addMember("method = %T.Method.GET", ClassNames.AutoMappingContract.toKClassName())
                                    .addMember(
                                        "beanType = %T::class",
                                        config.useCaseAdminSearchClassName.toKClassName()
                                    )
                                    .addMember("consumes = []")
                                    .build()
                            )
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("create")
                            .addModifiers(KModifier.ABSTRACT)
                            .addKdoc("新增${pojoClass.comment}")
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AdminPermissionScope.toKClassName())
                                    .addMember("%S", "${config.adminResourcePrefix}create")
                                    .build()
                            )
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AutoMappingContract.toKClassName())
                                    .addMember(
                                        "beanType = %T::class",
                                        config.useCaseAdminCreateClassName.toKClassName()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("update")
                            .addModifiers(KModifier.ABSTRACT)
                            .addKdoc("编辑${pojoClass.comment}")
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AdminPermissionScope.toKClassName())
                                    .addMember("%S", "${config.adminResourcePrefix}update")
                                    .build()
                            )
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AutoMappingContract.toKClassName())
                                    .addMember(
                                        "beanType = %T::class",
                                        config.useCaseAdminUpdateClassName.toKClassName()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .addFunction(
                        FunSpec.builder("delete")
                            .addModifiers(KModifier.ABSTRACT)
                            .addKdoc("删除${pojoClass.comment}")
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AdminPermissionScope.toKClassName())
                                    .addMember("%S", "${config.adminResourcePrefix}delete")
                                    .build()
                            )
                            .addAnnotation(
                                AnnotationSpec.builder(ClassNames.AutoMappingContract.toKClassName())
                                    .addMember(
                                        "beanType = %T::class",
                                        config.useCaseAdminDeleteClassName.toKClassName()
                                    )
                                    .build()
                            )
                            .build()
                    )
                    .build()
            )
            .indent(config.indent)
            .build()

        file.writeTo(File(config.gatewayKotlinOutputDir))
    }

}