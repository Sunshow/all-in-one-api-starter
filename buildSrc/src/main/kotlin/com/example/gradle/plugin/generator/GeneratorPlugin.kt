package com.example.gradle.plugin.generator

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.extension.GenerateConfigPluginExtension
import com.example.gradle.plugin.generator.job.*
import com.example.gradle.plugin.generator.util.GenerateUtils
import com.thoughtworks.qdox.JavaProjectBuilder
import com.thoughtworks.qdox.model.JavaClass
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import java.io.FileReader

class GeneratorPlugin : Plugin<Project> {

    private val prefix = "turboInfo"

    private lateinit var config: GenerateConfig

    private lateinit var pojoClass: JavaClass

    private val pojoFieldList by lazy {
        pojoClass.fields
            .filter {
                // 只处理 private 非 static 非 final 的属性
                it.isPrivate && !it.isStatic && !it.isFinal
            }
    }

    override fun apply(project: Project) {

        val extension =
            project.extensions.create<GenerateConfigPluginExtension>("${prefix}GeneratorConfigPluginExtension")
        extension.softDelete.convention(true)
        extension.adminParentPermissionName.convention("")

        val parse = project.task("${prefix}GeneratePreParse") {
            doFirst {
                println("GeneratePreParse: start")
            }
            doLast {
                val basePackage = extension.basePackage.get()
                val basePartition = extension.basePartition.get()
                val baseOutputDir = project.rootProject.rootDir.absolutePath
                val baseModulePrefix = "api-"
                val pojoName = extension.pojoName.get()
                val pojoBIModule = extension.pojoBIModule.get()
                val softDelete = extension.softDelete.get()

                config = GenerateConfig(
                    basePackage = basePackage,
                    basePartition = basePartition,
                    baseOutputDir = baseOutputDir,
                    baseModulePrefix = baseModulePrefix,

                    pojoName = pojoName,
                    pojoBIModule = pojoBIModule,
                    softDelete = softDelete,
                    adminParentPermissionName = extension.adminParentPermissionName.get(),
                )

                // 根据 QBean 属性生成代码
                val src = JavaProjectBuilder().addSource(
                    FileReader(
                        String.format(
                            "%s/%s/%s.java",
                            config.entityJavaOutputDir,
                            GenerateUtils.packageNameToPath(config.pojoPackage),
                            config.pojoName
                        )
                    )
                )

                pojoClass = src.classes[0]
                for (field in pojoFieldList) {
                    println("${field.name} [${field.comment}]: ${field.type}")
                }

                println("GeneratePreParse: done")
            }
        }

        val generateService = project.task("${prefix}GenerateService") {
            doFirst {
                println("GenerateService: start")
            }

            doLast {
                GenerateServiceJob.generate(config)

                println("GenerateService: done")
            }
        }
        generateService.dependsOn(parse)


        val generateRepositoryEntity = project.task("${prefix}GenerateRepositoryEntity") {
            doFirst {
                println("GenerateRepositoryEntity: start")
            }

            doLast {
                GenerateRepositoryEntityJob.generate(config, pojoClass, pojoFieldList)

                println("GenerateRepositoryEntity: done")
            }
        }
        generateRepositoryEntity.dependsOn(parse)

        val generateRepository = project.task("${prefix}GenerateRepository") {
            doFirst {
                println("GenerateRepository: start")
            }

            doLast {
                GenerateRepositoryJob.generate(config)

                println("GenerateRepository: done")
            }
        }
        generateRepository.dependsOn(parse)

        val generateServiceImpl = project.task("${prefix}GenerateServiceImpl") {
            doFirst {
                println("GenerateServiceImpl: start")
            }

            doLast {
                GenerateServiceImplJob.generate(config)

                println("GenerateServiceImpl: done")
            }
        }
        generateServiceImpl.dependsOn(parse)

        val generateSQLCreateTable = project.task("${prefix}GenerateSQLCreateTable") {
            doFirst {
                println("GenerateSQLCreateTable: start")
            }

            doLast {
                GenerateSQLCreateTableJob.generate(config, pojoClass, pojoFieldList)

                println("GenerateSQLCreateTable: done")
            }
        }
        generateSQLCreateTable.dependsOn(parse)

        val generateUseCaseAdminSearch = project.task("${prefix}GenerateUseCaseAdminSearch") {
            doFirst {
                println("GenerateUseCaseAdminSearch: start")
            }

            doLast {
                GenerateUseCaseAdminSearchJob.generate(config, pojoClass, pojoFieldList)

                println("GenerateUseCaseAdminSearch: done")
            }
        }
        generateUseCaseAdminSearch.dependsOn(parse)

        val generateUseCaseAdminCreate = project.task("${prefix}GenerateUseCaseAdminCreate") {
            doFirst {
                println("GenerateUseCaseAdminCreate: start")
            }

            doLast {
                GenerateUseCaseAdminCreateJob.generate(config, pojoClass, pojoFieldList)

                println("GenerateUseCaseAdminCreate: done")
            }
        }
        generateUseCaseAdminCreate.dependsOn(parse)

        val generateUseCaseAdminUpdate = project.task("${prefix}GenerateUseCaseAdminUpdate") {
            doFirst {
                println("GenerateUseCaseAdminUpdate: start")
            }

            doLast {
                GenerateUseCaseAdminUpdateJob.generate(config, pojoClass, pojoFieldList)

                println("GenerateUseCaseAdminUpdate: done")
            }
        }
        generateUseCaseAdminUpdate.dependsOn(parse)

        val generateUseCaseAdminDelete = project.task("${prefix}GenerateUseCaseAdminDelete") {
            doFirst {
                println("GenerateUseCaseAdminDelete: start")
            }

            doLast {
                GenerateUseCaseAdminDeleteJob.generate(config, pojoClass)

                println("GenerateUseCaseAdminDelete: done")
            }
        }
        generateUseCaseAdminDelete.dependsOn(parse)

        val generateMappingContractAdmin = project.task("${prefix}GenerateMappingContractAdmin") {
            doFirst {
                println("GenerateMappingContractAdmin: start")
            }

            doLast {
                GenerateMappingContractAdminJob.generate(config, pojoClass)

                println("GenerateMappingContractAdmin: done")
            }
        }
        generateMappingContractAdmin.dependsOn(parse)

        val generateSQLAdminPermission = project.task("${prefix}GenerateSQLAdminPermission") {
            doFirst {
                println("GenerateSQLAdminPermission: start")
            }

            doLast {
                GenerateSQLAdminPermissionJob.generate(config, pojoClass)

                println("GenerateSQLAdminPermission: done")
            }
        }
        generateSQLAdminPermission.dependsOn(parse)

        val generateAllWithoutAdmin = project.task("${prefix}GenerateAllWithoutAdmin") {
            doLast {
                println("GenerateAllWithoutAdmin: done")
            }
        }

        generateAllWithoutAdmin.dependsOn(
            generateService,
            generateRepositoryEntity,
            generateRepository,
            generateServiceImpl,
            generateSQLCreateTable,
        )


        val generateAll = project.task("${prefix}GenerateAll") {
            doLast {
                println("GenerateAll: done")
            }
        }

        generateAll.dependsOn(
            generateAllWithoutAdmin,
            generateUseCaseAdminSearch,
            generateUseCaseAdminCreate,
            generateUseCaseAdminUpdate,
            generateUseCaseAdminDelete,
            generateMappingContractAdmin,
            GenerateSQLAdminPermissionJob,
        )
    }
}