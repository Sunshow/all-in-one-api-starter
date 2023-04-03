package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.example.gradle.plugin.generator.util.className
import com.squareup.javapoet.*
import com.thoughtworks.qdox.model.JavaClass
import com.thoughtworks.qdox.model.JavaField
import java.io.File
import javax.lang.model.element.Modifier

object GenerateUseCaseAdminSearchJob {

    fun generate(config: GenerateConfig, pojoClass: JavaClass, pojoFieldList: List<JavaField>) {
        val baseUseCaseTypeName =
            ParameterizedTypeName.get(
                ClassNames.AbstractUseCase,
                config.useCaseAdminSearchInputClassName,
                config.useCaseAdminSearchOutputClassName
            )

        val typeSpecBuilder = TypeSpec.classBuilder(config.useCaseAdminSearchName)
            .superclass(baseUseCaseTypeName)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(ClassNames.LombokSlf4j)
            .addAnnotation(ClassNames.LombokRequiredArgsConstructor)
            .addAnnotation(ClassNames.SpringComponent)
            .addJavadoc("${pojoClass.comment}查询")

        // 添加默认 Service
        typeSpecBuilder.addField(
            config.serviceClassName,
            config.serviceInstance,
            Modifier.PRIVATE,
            Modifier.FINAL
        )

        // 实现抽象方法
        val methodSpec = MethodSpec.methodBuilder("doAction")
            .addModifiers(Modifier.PROTECTED)
            .addAnnotation(Override::class.java)
            .returns(config.useCaseAdminSearchOutputClassName)
            .addParameter(config.useCaseAdminSearchInputClassName, "input")
            .addStatement("PageSearch search = input.getSearch()")
            .addStatement("QResponse<\$T> result = \$N.search(search)", pojoClass.className(), config.serviceInstance)
            .addStatement(
                "return OutputData.builder()\n.result(result)\n.build()"
            )
            .build()
        typeSpecBuilder.addMethod(methodSpec)

        // 添加内部类 Input
        val innerInputBuilder = TypeSpec.classBuilder("InputData")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addAnnotation(
                AnnotationSpec.builder(ClassNames.LombokEqualsAndHashCode)
                    .addMember("callSuper", "true")
                    .build()
            )
            .addAnnotation(ClassNames.LombokData)
            .addAnnotation(ClassNames.LombokNoArgsConstructor)
            .addAnnotation(ClassNames.LombokAllArgsConstructor)
            .addAnnotation(ClassNames.LombokBuilder)
            .superclass(ClassNames.AbstractUseCaseInputData)
        // 添加参数
        innerInputBuilder.addField(
            FieldSpec.builder(ClassNames.PageSearch, "search", Modifier.PRIVATE)
                .addAnnotation(
                    AnnotationSpec.builder(ClassNames.JavaxNotNull)
                        .addMember("message", "\$S", "查询参数不能为空")
                        .build()
                )
                .addAnnotation(
                    AnnotationSpec.builder(ClassNames.QSearch)
                        .addMember("definition", "\$T.class", config.useCaseAdminSearchDefClassName)
                        .build()
                )
                .build()
        )
        typeSpecBuilder.addType(innerInputBuilder.build())

        // 添加内部类 Output
        val innerOutputBuilder = TypeSpec.classBuilder("OutputData")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addAnnotation(ClassNames.LombokGetter)
            .addAnnotation(ClassNames.LombokSetter)
            .addAnnotation(ClassNames.LombokBuilder)
            .superclass(ClassNames.AbstractUseCaseOutputData)
        // 添加返回值
        innerOutputBuilder.addField(
            ParameterizedTypeName.get(
                ClassNames.QResponse,
                config.pojoClassName,
            ),
            "result",
            Modifier.PRIVATE
        )
        typeSpecBuilder.addType(innerOutputBuilder.build())

        // 添加内部类 SearchDef
        val innerSearchDefBuilder = TypeSpec.classBuilder(config.useCaseAdminSearchDefName)
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addAnnotation(ClassNames.LombokData)
            .addAnnotation(ClassNames.QField)
        // 添加查询条件
        pojoFieldList
            .filter {
                // 默认不生成创建时间和更新时间的查询条件
                it.name != "createdTime" && it.name != "updatedTime"
            }
            .map {
                val fieldName = it.name
                val fieldType = it.type
                FieldSpec.builder(fieldType.className(), fieldName, Modifier.PRIVATE)
                    .apply {
                        if (fieldName == "id") {
                            // 把 ID 作为默认排序字段
                            addAnnotation(
                                AnnotationSpec.builder(ClassNames.QField)
                                    .addMember("defaultSort", "true")
                                    .addMember("desc", "true")
                                    .build()
                            )
                        }
                        // 字符串默认生成 LIKE 条件
                        else if (fieldType.className() == ClassNames.JavaString) {
                            addAnnotation(
                                AnnotationSpec.builder(ClassNames.QField)
                                    .addMember("operator", "\$T.LIKE", ClassNames.Operator)
                                    .build()
                            )
                        }
                    }
                    .build()
            }
            .onEach {
                innerSearchDefBuilder.addField(it)
            }
        typeSpecBuilder.addType(innerSearchDefBuilder.build())

        val javaFile =
            JavaFile.builder(config.useCaseAdminPackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.domainJavaOutputDir))
    }

}