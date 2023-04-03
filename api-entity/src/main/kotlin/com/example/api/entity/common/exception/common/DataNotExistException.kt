package com.example.api.entity.common.exception.common

open class DataNotExistException(message: String = "数据已存在", cause: Throwable? = null) : RuntimeException(message, cause)
