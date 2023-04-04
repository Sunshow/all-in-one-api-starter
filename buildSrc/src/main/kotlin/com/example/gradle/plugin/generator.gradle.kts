package com.example.gradle.plugin

import com.example.gradle.plugin.generator.GeneratorPlugin
import com.example.gradle.plugin.generator.extension.GenerateConfigPluginExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

apply<GeneratorPlugin>()

configure<GenerateConfigPluginExtension> {
    basePackage.set("com.example.api")
    // pojo 和 service 生成的所属大模块, 例如: common, admin, front
    basePartition.set("admin")
    pojoName.set("Role")
    // 业务模块
    pojoBIModule.set("role")
    softDelete.set(true)
    // 后台父级权限名称, 已存在就生成在父级权限下, 不存在就自动创建父级权限 (两种SQL都会生成, 请自行选择)
    adminParentPermissionName.set("系统管理")
}