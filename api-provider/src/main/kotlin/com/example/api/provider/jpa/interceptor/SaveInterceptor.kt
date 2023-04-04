package com.example.api.provider.jpa.interceptor

import org.hibernate.type.Type
import java.io.Serializable

interface SaveInterceptor {

    fun onSave(
        entity: Any,
        id: Serializable?,
        state: Array<Any>,
        propertyNames: Array<out String>,
        types: Array<out Type>
    ): Boolean = false

    fun isSupported(
        entity: Any,
        id: Serializable?,
        state: Array<out Any>,
        propertyNames: Array<out String>,
        types: Array<out Type>
    ): Boolean = false

}
