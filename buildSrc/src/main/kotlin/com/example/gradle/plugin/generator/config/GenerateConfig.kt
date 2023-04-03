package com.example.gradle.plugin.generator.config

import com.example.gradle.plugin.generator.util.GenerateUtils
import com.squareup.javapoet.ClassName

data class GenerateConfig(
    val indent: String = "    ",

    val basePackage: String,
    val basePartition: String,
    val baseOutputDir: String,
    val baseModulePrefix: String,

    val pojoName: String,
    val pojoBIModule: String,

    val softDelete: Boolean = true,
    val adminParentPermissionName: String = "",
) {
    val entityModule: String = baseModulePrefix + "entity"
    val entityPackage: String = "$basePackage.entity"
    val entityJavaOutputDir: String = "$baseOutputDir/$entityModule/src/main/java"

    val domainModule: String = baseModulePrefix + "domain"
    val domainPackage: String = "$basePackage.domain"
    val domainJavaOutputDir: String = "$baseOutputDir/$domainModule/src/main/java"

    val providerModule: String = baseModulePrefix + "provider"
    val providerPackage: String = "$basePackage.provider"
    val providerJavaOutputDir: String = "$baseOutputDir/$providerModule/src/main/java"

    val gatewayModule: String = baseModulePrefix + "gateway"
    val gatewayPackage: String = "$basePackage.gateway"
    val gatewayJavaOutputDir: String = "$baseOutputDir/$gatewayModule/src/main/java"
    val gatewayKotlinOutputDir: String = "$baseOutputDir/$gatewayModule/src/main/kotlin"

    val pojoPackage: String = "$entityPackage.$basePartition.pojo.$pojoBIModule"
    val pojoCreatorName: String = "${pojoName}Creator"
    val pojoCreatorClassName: ClassName = ClassName.get(pojoPackage, pojoCreatorName)
    val pojoUpdaterName: String = "${pojoName}Updater"
    val pojoUpdaterClassName: ClassName = ClassName.get(pojoPackage, pojoUpdaterName)

    val serviceName: String = "${pojoName}Service"
    val servicePackage: String = "$domainPackage.$basePartition.service.$pojoBIModule"
    val serviceClassName: ClassName = ClassName.get(servicePackage, serviceName)
    val serviceInstance: String = GenerateUtils.upperCamelToLowerCamel(serviceName)

    val pojoClassName: ClassName = ClassName.get(pojoPackage, pojoName)
    val pojoInstance: String = GenerateUtils.upperCamelToLowerCamel(pojoName)
    val pojoIdInstance: String = "${pojoInstance}Id"

    val repositoryEntityName: String = "${pojoName}PO"
    val repositoryEntityPackage: String = "$providerPackage.$basePartition.repository.database.$pojoBIModule"
    val repositoryEntityClassName: ClassName = ClassName.get(repositoryEntityPackage, repositoryEntityName)

    val repositoryName: String = "${pojoName}DAO"
    val repositoryPackage: String = "$providerPackage.$basePartition.repository.database.$pojoBIModule"
    val repositoryClassName: ClassName = ClassName.get(repositoryPackage, repositoryName)

    val serviceImplName: String = "${pojoName}ServiceImpl"
    val serviceImplPackage: String = "$providerPackage.$basePartition.service.impl.$pojoBIModule"

    val tableName: String = GenerateUtils.upperCamelToLowerUnderScore(pojoName)
    val sqlOutputDir = "$baseOutputDir/$providerModule/sql"

    val useCaseAdminPackage: String = "$domainPackage.admin.usecase.$pojoBIModule"

    val useCaseAdminSearchName: String = "Admin${pojoName}SearchUseCase"
    val useCaseAdminSearchClassName: ClassName = ClassName.get(useCaseAdminPackage, useCaseAdminSearchName)
    val useCaseAdminSearchInputClassName: ClassName = useCaseAdminSearchClassName.nestedClass("InputData")
    val useCaseAdminSearchOutputClassName: ClassName = useCaseAdminSearchClassName.nestedClass("OutputData")
    val useCaseAdminSearchDefName: String = "${pojoName}SearchDef"
    val useCaseAdminSearchDefClassName: ClassName = useCaseAdminSearchClassName.nestedClass(useCaseAdminSearchDefName)

    val useCaseAdminCreateName: String = "Admin${pojoName}CreateUseCase"
    val useCaseAdminCreateClassName: ClassName = ClassName.get(useCaseAdminPackage, useCaseAdminCreateName)
    val useCaseAdminCreateInputClassName: ClassName = useCaseAdminCreateClassName.nestedClass("InputData")
    val useCaseAdminCreateOutputClassName: ClassName = useCaseAdminCreateClassName.nestedClass("OutputData")

    val useCaseAdminUpdateName: String = "Admin${pojoName}UpdateUseCase"
    val useCaseAdminUpdateClassName: ClassName = ClassName.get(useCaseAdminPackage, useCaseAdminUpdateName)
    val useCaseAdminUpdateInputClassName: ClassName = useCaseAdminUpdateClassName.nestedClass("InputData")
    val useCaseAdminUpdateOutputClassName: ClassName = useCaseAdminUpdateClassName.nestedClass("OutputData")

    val useCaseAdminDeleteName: String = "Admin${pojoName}DeleteUseCase"
    val useCaseAdminDeleteClassName: ClassName = ClassName.get(useCaseAdminPackage, useCaseAdminDeleteName)
    val useCaseAdminDeleteInputClassName: ClassName = useCaseAdminDeleteClassName.nestedClass("InputData")
    val useCaseAdminDeleteOutputClassName: ClassName = useCaseAdminDeleteClassName.nestedClass("OutputData")

    val adminContractName: String = "Admin${pojoName}Contract"
    val adminContractPackage: String = "$gatewayPackage.admin.contract.$pojoBIModule"

    //val adminResourcePrefix: String = "admin${pojoName}:"
    val adminResourcePrefix: String = "${pojoInstance}:"
}