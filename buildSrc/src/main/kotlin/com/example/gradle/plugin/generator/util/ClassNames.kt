package com.example.gradle.plugin.generator.util

import com.squareup.javapoet.ClassName

object ClassNames {

    // Custom Base
    val QEntity: ClassName = ClassName.get("cn.turboinfo.solarkorb.api.provider.common.repository.database", "QEntity")
    val SoftDeleteQEntity: ClassName =
        ClassName.get("cn.turboinfo.solarkorb.api.provider.common.repository.database", "SoftDeleteQEntity")
    val QRepository: ClassName =
        ClassName.get("cn.turboinfo.solarkorb.api.provider.common.repository.database", "QRepository")
    val AbstractUseCase: ClassName =
        ClassName.get("cn.turboinfo.solarkorb.api.domain.common.usecase", "AbstractUseCase")
    val AbstractUseCaseOutputData: ClassName = AbstractUseCase.nestedClass("OutputData")
    val AbstractUseCaseInputData: ClassName = AbstractUseCase.nestedClass("InputData")

    // Admin
    val AdminSessionScope: ClassName =
        ClassName.get("cn.turboinfo.solarkorb.api.gateway.admin.framework.http.annotation", "AdminSessionScope")
    val AdminPermissionScope: ClassName =
        ClassName.get("cn.turboinfo.solarkorb.api.gateway.admin.framework.http.annotation", "AdminPermissionScope")

    // Toolkit
    val BaseQService: ClassName = ClassName.get("net.sunshow.toolkit.core.qbean.api.service", "BaseQService")
    val DefaultQServiceImpl: ClassName =
        ClassName.get("net.sunshow.toolkit.core.qbean.helper.service.impl", "DefaultQServiceImpl")
    val QField: ClassName = ClassName.get("net.sunshow.toolkit.core.qbean.api.annotation", "QField")
    val QResponse: ClassName = ClassName.get("net.sunshow.toolkit.core.qbean.api.response", "QResponse")
    val Operator: ClassName = ClassName.get("net.sunshow.toolkit.core.qbean.api.enums", "Operator")
    val QSearch: ClassName = ClassName.get("net.sunshow.toolkit.core.qbean.api.annotation", "QSearch")
    val PageSearch: ClassName = ClassName.get("net.sunshow.toolkit.core.qbean.api.search", "PageSearch")
    val QBeanCreatorHelper: ClassName =
        ClassName.get("net.sunshow.toolkit.core.qbean.helper.component.request", "QBeanCreatorHelper")
    val QBeanUpdaterHelper: ClassName =
        ClassName.get("net.sunshow.toolkit.core.qbean.helper.component.request", "QBeanUpdaterHelper")

    // NXCloud
    val EnableSoftDelete: ClassName =
        ClassName.get("nxcloud.foundation.core.data.support.annotation", "EnableSoftDelete")
    val AutoMappingContract: ClassName =
        ClassName.get("nxcloud.ext.springmvc.automapping.base.annotation", "AutoMappingContract")

    // Java Lang
    val JavaInteger: ClassName = ClassName.get(Int::class.javaObjectType)
    val JavaLong: ClassName = ClassName.get(Long::class.javaObjectType)
    val JavaString: ClassName = ClassName.get(String::class.java)

    // Javax
    val JavaxNotNull: ClassName = ClassName.get("javax.validation.constraints", "NotNull")
    val JavaxPositive: ClassName = ClassName.get("javax.validation.constraints", "Positive")
    val JavaxNotBlank: ClassName = ClassName.get("javax.validation.constraints", "NotBlank")

    // JPA
    val JpaTable: ClassName = ClassName.get("javax.persistence", "Table")
    val JpaEntity: ClassName = ClassName.get("javax.persistence", "Entity")

    // Hibernate
    val HibernateDynamicInsert: ClassName = ClassName.get("org.hibernate.annotations", "DynamicInsert")
    val HibernateDynamicUpdate: ClassName = ClassName.get("org.hibernate.annotations", "DynamicUpdate")

    // Lombok
    val LombokData: ClassName = ClassName.get("lombok", "Data")
    val LombokSetter: ClassName = ClassName.get("lombok", "Setter")
    val LombokGetter: ClassName = ClassName.get("lombok", "Getter")
    val LombokBuilder: ClassName = ClassName.get("lombok", "Builder")
    val LombokNoArgsConstructor: ClassName = ClassName.get("lombok", "NoArgsConstructor")
    val LombokAllArgsConstructor: ClassName = ClassName.get("lombok", "AllArgsConstructor")
    val LombokRequiredArgsConstructor: ClassName = ClassName.get("lombok", "RequiredArgsConstructor")
    val LombokSlf4j: ClassName = ClassName.get("lombok.extern.slf4j", "Slf4j")
    val LombokEqualsAndHashCode: ClassName = ClassName.get("lombok", "EqualsAndHashCode")

    // Spring
    val SpringService: ClassName = ClassName.get("org.springframework.stereotype", "Service")
    val SpringComponent: ClassName = ClassName.get("org.springframework.stereotype", "Component")
}