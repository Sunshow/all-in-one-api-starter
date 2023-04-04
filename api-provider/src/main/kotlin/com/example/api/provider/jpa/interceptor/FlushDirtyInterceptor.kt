package com.example.api.provider.jpa.interceptor

import org.hibernate.type.Type
import java.io.Serializable

interface FlushDirtyInterceptor {

    fun onFlushDirty(
        entity: Any,
        id: Serializable,
        currentState: Array<Any>,
        previousState: Array<out Any>,
        propertyNames: Array<out String>,
        types: Array<out Type>
    ): Boolean = false

    fun isSupported(
        entity: Any,
        id: Serializable,
        currentState: Array<out Any>,
        previousState: Array<out Any>,
        propertyNames: Array<out String>,
        types: Array<out Type>
    ): Boolean = false

}
