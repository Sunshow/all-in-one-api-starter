package com.example.api.provider.jpa.interceptor

import nxcloud.foundation.core.data.jpa.interceptor.EmptyJpaSessionFactoryInterceptor
import nxcloud.foundation.core.spring.support.SpringContextHelper
import org.hibernate.type.Type
import java.io.Serializable

class DefaultJpaSessionFactoryInterceptor : EmptyJpaSessionFactoryInterceptor() {

    private var flushDirtyInterceptors: List<FlushDirtyInterceptor>? = null

    private var saveInterceptors: List<SaveInterceptor>? = null

    override fun onSave(
        entity: Any,
        id: Serializable?,
        state: Array<Any>,
        propertyNames: Array<out String>,
        types: Array<out Type>
    ): Boolean {
        if (saveInterceptors == null) {
            saveInterceptors =
                SpringContextHelper.getBeansOfType(SaveInterceptor::class.java).values.toList()
        }

        var result = false
        saveInterceptors!!
            .filter {
                it.isSupported(entity, id, state, propertyNames, types)
            }
            .onEach {
                if (it.onSave(entity, id, state, propertyNames, types)) {
                    result = true
                }
            }

        return result
    }

    override fun onFlushDirty(
        entity: Any,
        id: Serializable,
        currentState: Array<Any>,
        previousState: Array<out Any>,
        propertyNames: Array<out String>,
        types: Array<out Type>
    ): Boolean {
        if (flushDirtyInterceptors == null) {
            flushDirtyInterceptors =
                SpringContextHelper.getBeansOfType(FlushDirtyInterceptor::class.java).values.toList()
        }

        var result = false
        flushDirtyInterceptors!!
            .filter {
                it.isSupported(entity, id, currentState, previousState, propertyNames, types)
            }
            .onEach {
                if (it.onFlushDirty(entity, id, currentState, previousState, propertyNames, types)) {
                    result = true
                }
            }

        return result
    }
}
