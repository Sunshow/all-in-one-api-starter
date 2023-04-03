package com.example.gradle.plugin.generator.job

import com.example.gradle.plugin.generator.config.GenerateConfig
import com.thoughtworks.qdox.model.JavaClass
import java.io.FileWriter

object GenerateSQLAdminPermissionJob {

    fun generate(config: GenerateConfig, pojoClass: JavaClass) {
        if (config.adminParentPermissionName.isNullOrBlank()) {
            println("未设置父级权限名称, 跳过权限生成")
            return
        }
        val parentPermissionName = config.adminParentPermissionName
        val name = pojoClass.comment ?: pojoClass.name

        val sql = buildString {
            appendLine()
            appendLine("-- ${name}管理")
            appendLine("-- 创建父级权限")
            appendLine("INSERT INTO `sys_permission` (`name`, `resource`, `url`, `component`, `component_name`, `visible_status`, `parent_id`)")
            appendLine("VALUES ('${parentPermissionName}', '${config.adminResourcePrefix}null', '', '/${config.pojoInstance}', '', 1, 0)")
            appendLine("SET @${config.pojoInstance}PermissionParentId = LAST_INSERT_ID();")
            appendLine("-- 查找父级权限")
            appendLine("SET @${config.pojoInstance}PermissionParentId = (select `id` from `sys_permissio`n where `name`='${parentPermissionName}');")
            appendLine("INSERT INTO `sys_permission` (`name`, `resource`, `url`, `component`, `component_name`, `visible_status`, `parent_id`)")
            appendLine("VALUES ('${name}管理', '${config.adminResourcePrefix}list', '', '/${config.pojoInstance}/list', '', 1, @${config.pojoInstance}PermissionParentId),")
            appendLine("       ('新增${name}', '${config.adminResourcePrefix}create', '', '', '', 0, @${config.pojoInstance}PermissionParentId),")
            appendLine("       ('更新${name}', '${config.adminResourcePrefix}update', '', '', '', 0, @${config.pojoInstance}PermissionParentId),")
            appendLine("       ('删除${name}', '${config.adminResourcePrefix}delete', '', '', '', 0, @${config.pojoInstance}PermissionParentId);")
        }

        println(sql)

        // 追加到文件末尾
        val outputFilename = "${config.sqlOutputDir}/permission.sql"

        val fileWriter = FileWriter(outputFilename, true)
        fileWriter.write(sql)
        fileWriter.close()
    }

}