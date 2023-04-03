package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.example.gradle.plugin.generator.util.className
import com.squareup.javapoet.*
import com.thoughtworks.qdox.model.JavaClass
import com.thoughtworks.qdox.model.JavaField
import java.io.File
import javax.lang.model.element.Modifier

object GenerateUseCaseAdminUpdateJob {

    fun generate(config: GenerateConfig, pojoClass: JavaClass, pojoFieldList: List<JavaField>) {
        val baseUseCaseTypeName =
            ParameterizedTypeName.get(
                ClassNames.AbstractUseCase,
                config.useCaseAdminUpdateInputClassName,
                config.useCaseAdminUpdateOutputClassName
            )

        val typeSpecBuilder = TypeSpec.classBuilder(config.useCaseAdminUpdateName)
            .superclass(baseUseCaseTypeName)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(ClassNames.LombokSlf4j)
            .addAnnotation(ClassNames.LombokRequiredArgsConstructor)
            .addAnnotation(ClassNames.SpringComponent)
            .addJavadoc("${pojoClass.comment}更新")

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
            .returns(config.useCaseAdminUpdateOutputClassName)
            .addParameter(config.useCaseAdminUpdateInputClassName, "input")
            .addStatement(
                "\$T \$N = input.get\$N()",
                ClassNames.JavaLong,
                config.pojoIdInstance,
                config.pojoIdInstance.capitalize()
            )
            .addStatement(
                "\$T.Builder builder = \$T.builder(\$N)",
                config.pojoUpdaterClassName,
                config.pojoUpdaterClassName,
                config.pojoIdInstance
            )
            .addStatement(
                "\$T.copyPropertiesToUpdateBuilder(builder, \$T.class, input)",
                ClassNames.QBeanUpdaterHelper,
                config.pojoUpdaterClassName
            )
            .addStatement(
                "\$T \$N = \$N.update(builder.build())",
                config.pojoClassName,
                config.pojoInstance,
                config.serviceInstance
            )
            .addStatement(
                "return OutputData.builder()\n.\$N(\$N)\n.build()",
                config.pojoInstance,
                config.pojoInstance
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
        // ID 单独添加
        innerInputBuilder.addField(
            FieldSpec.builder(ClassNames.JavaLong, config.pojoIdInstance, Modifier.PRIVATE)
                .addAnnotation(
                    AnnotationSpec.builder(ClassNames.JavaxNotNull)
                        .addMember("message", "\$S", "ID不能为空")
                        .build()
                )
                .addAnnotation(ClassNames.JavaxPositive)
                .build()
        )
        // 添加参数
        pojoFieldList
            .filter {
                // 默认不生成ID和创建时间和更新时间的新增参数
                it.name != "id" && it.name != "createdTime" && it.name != "updatedTime"
            }
            .map {
                val fieldName = it.name
                val fieldType = it.type
                FieldSpec.builder(fieldType.className(), fieldName, Modifier.PRIVATE)
                    .apply {
                        // 字符串默认生成 NotBlank 约束
                        if (fieldType.className() == ClassNames.JavaString) {
                            addAnnotation(
                                AnnotationSpec.builder(ClassNames.JavaxNotBlank)
                                    .addMember("message", "\$S", "${it.comment}不能为空")
                                    .build()
                            )
                        } else if (fieldType.className() == ClassNames.JavaLong) {
                            addAnnotation(
                                AnnotationSpec.builder(ClassNames.JavaxNotNull)
                                    .addMember("message", "\$S", "${it.comment}不能为空")
                                    .build()
                            )
                            addAnnotation(ClassNames.JavaxPositive)
                        } else {
                            addAnnotation(
                                AnnotationSpec.builder(ClassNames.JavaxNotNull)
                                    .addMember("message", "\$S", "${it.comment}不能为空")
                                    .build()
                            )
                        }
                    }
                    .build()
            }
            .onEach {
                innerInputBuilder.addField(it)
            }
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
            FieldSpec.builder(config.pojoClassName, config.pojoInstance, Modifier.PRIVATE)
                .build()
        )
        typeSpecBuilder.addType(innerOutputBuilder.build())

        val javaFile =
            JavaFile.builder(config.useCaseAdminPackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.domainJavaOutputDir))
    }

}