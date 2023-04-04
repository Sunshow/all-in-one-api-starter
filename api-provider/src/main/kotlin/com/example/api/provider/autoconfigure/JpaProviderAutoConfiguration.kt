package com.example.api.provider.autoconfigure

import com.example.api.provider.jpa.interceptor.DefaultJpaSessionFactoryInterceptor
import nxcloud.foundation.core.data.jpa.interceptor.EmptyJpaSessionFactoryInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(value = ["com.example.api.provider.jpa"])
class JpaProviderAutoConfiguration {

    @Bean
    fun commonJpaSessionFactoryInterceptor(): EmptyJpaSessionFactoryInterceptor {
        return DefaultJpaSessionFactoryInterceptor()
    }

}