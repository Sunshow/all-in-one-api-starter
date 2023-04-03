package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.example.gradle.plugin.generator.util.GenerateUtils
import com.thoughtworks.qdox.model.JavaClass
import com.thoughtworks.qdox.model.JavaField
import java.io.FileWriter

object GenerateSQLCreateTableJob {

    private val javaTypeToDBMap = mapOf(
        "Long" to "BIGINT(1) NOT NULL DEFAULT 0",
        "Integer" to "INT(1) NOT NULL DEFAULT 0",
        "String" to "VARCHAR(255) NOT NULL DEFAULT ''",
        "LocalDateTime" to "DATETIME NULL",
    )

    private val fieldNameToDBMap = mapOf(
        "id" to "BIGINT(1) NOT NULL AUTO_INCREMENT",
        "deleted" to "BIGINT(1) NOT NULL DEFAULT 0",
        "createdTime" to "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP",
        "updatedTime" to "DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP",
    )

    private val enumToDB = "SMALLINT(1) NOT NULL DEFAULT 0"

    fun generate(config: GenerateConfig, pojoClass: JavaClass, pojoFieldList: List<JavaField>) {
        val tableName = config.tableName
        val tableComment = pojoClass.comment


        // 开始拼装输出的SQL
        val prefix = "CREATE TABLE `${tableName}`\n("

        // 逐个字段拼装
        val fields = pojoFieldList
            .filter {
                // 排除保留字段
                !fieldNameToDBMap.containsKey(it.name)
            }
            .joinToString("\n") {
                fieldToDB(it)
            }

        val suffix =
            ") ENGINE=InnoDB\n  DEFAULT CHARSET=utf8mb4\n  COMMENT='${tableComment}';"

        val sql = buildString {
            appendLine()
            appendLine(prefix)
            appendLine(reservedFieldToDB("id"))
            appendLine(fields)
            if (config.softDelete) {
                appendLine(reservedFieldToDB("deleted"))
            }
            appendLine(reservedFieldToDB("createdTime"))
            appendLine(reservedFieldToDB("updatedTime"))
            appendLine("    PRIMARY KEY (`id`)")
            appendLine(suffix)
        }

        println(sql)

        // 追加到文件末尾
        val outputFilename = "${config.sqlOutputDir}/${config.pojoBIModule}.sql"

        val fileWriter = FileWriter(outputFilename, true)
        fileWriter.write(sql)
        fileWriter.close()
    }

    private fun reservedFieldToDB(fieldName: String): String {
        val columnName = GenerateUtils.lowerCamelToLowerUnderScore(fieldName)

        return "    `$columnName` ${fieldNameToDBMap[fieldName]!!},"
    }

    private fun fieldToDB(field: JavaField): String {
        val fieldName = field.name
        val fieldType = field.type.value

        val columnName = GenerateUtils.lowerCamelToLowerUnderScore(fieldName)

        val dbType = when {
            fieldNameToDBMap.containsKey(fieldName) -> fieldNameToDBMap[fieldName]
            javaTypeToDBMap.containsKey(fieldType) -> javaTypeToDBMap[fieldType]
            else -> enumToDB
        }

        return "    `$columnName` $dbType,"
    }
}