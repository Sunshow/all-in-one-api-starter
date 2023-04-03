package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.ClassNames
import com.squareup.javapoet.*
import com.thoughtworks.qdox.model.JavaClass
import java.io.File
import javax.lang.model.element.Modifier

object GenerateUseCaseAdminDeleteJob {

    fun generate(config: GenerateConfig, pojoClass: JavaClass) {
        val baseUseCaseTypeName =
            ParameterizedTypeName.get(
                ClassNames.AbstractUseCase,
                config.useCaseAdminDeleteInputClassName,
                config.useCaseAdminDeleteOutputClassName
            )

        val typeSpecBuilder = TypeSpec.classBuilder(config.useCaseAdminDeleteName)
            .superclass(baseUseCaseTypeName)
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(ClassNames.LombokSlf4j)
            .addAnnotation(ClassNames.LombokRequiredArgsConstructor)
            .addAnnotation(ClassNames.SpringComponent)
            .addJavadoc("${pojoClass.comment}删除")

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
            .returns(config.useCaseAdminDeleteOutputClassName)
            .addParameter(config.useCaseAdminDeleteInputClassName, "input")
            .addStatement(
                "\$T \$N = input.get\$N()",
                ClassNames.JavaLong,
                config.pojoIdInstance,
                config.pojoIdInstance.capitalize()
            )
            .addStatement(
                "\$N.deleteById(\$N);",
                config.serviceInstance,
                config.pojoIdInstance
            )
            .addStatement(
                "return OutputData.builder()\n.build()",
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
        typeSpecBuilder.addType(innerInputBuilder.build())

        // 添加内部类 Output
        val innerOutputBuilder = TypeSpec.classBuilder("OutputData")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .addAnnotation(ClassNames.LombokGetter)
            .addAnnotation(ClassNames.LombokSetter)
            .addAnnotation(ClassNames.LombokBuilder)
            .superclass(ClassNames.AbstractUseCaseOutputData)
        typeSpecBuilder.addType(innerOutputBuilder.build())

        val javaFile =
            JavaFile.builder(config.useCaseAdminPackage, typeSpecBuilder.build())
                .indent(config.indent)
                .skipJavaLangImports(true)
                .build()

        javaFile.writeTo(File(config.domainJavaOutputDir))
    }

}