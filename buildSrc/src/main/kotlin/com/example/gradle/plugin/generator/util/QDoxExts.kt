package com.example.gradle.plugin.generator.util

import com.squareup.javapoet.ClassName
import com.thoughtworks.qdox.model.JavaClass

fun JavaClass.className(): ClassName {
    return ClassName.get(this.packageName, this.name)
}