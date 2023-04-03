package com.example.gradle.plugin.generator.util

import com.google.common.base.CaseFormat

object GenerateUtils {

    fun packageNameToPath(packageName: String): String {
        return packageName.replace(".", "/")
    }

    fun upperCamelToLowerUnderScore(upperCamel: String): String {
        return CaseFormat.UPPER_CAMEL
            .to(
                CaseFormat.LOWER_UNDERSCORE,
                upperCamel
            )
    }

    fun lowerCamelToLowerUnderScore(lowerCamel: String): String {
        return CaseFormat.LOWER_CAMEL
            .to(
                CaseFormat.LOWER_UNDERSCORE,
                lowerCamel
            )
    }

    fun upperCamelToLowerCamel(upperCamel: String): String {
        return CaseFormat.UPPER_CAMEL
            .to(
                CaseFormat.LOWER_CAMEL,
                upperCamel
            )
    }
}