package com.example.gradle.plugin.generator.extension

import org.gradle.api.provider.Property

interface GenerateConfigPluginExtension {

    val basePackage: Property<String>

    val basePartition: Property<String>

    val pojoName: Property<String>

    val pojoBIModule: Property<String>

    val softDelete: Property<Boolean>

    val adminParentPermissionName: Property<String>
    
}