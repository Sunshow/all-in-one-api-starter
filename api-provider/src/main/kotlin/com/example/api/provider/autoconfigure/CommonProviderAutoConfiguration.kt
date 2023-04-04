package com.example.api.provider.autoconfigure

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(value = ["com.example.api.provider.common", "com.example.api.domain.common"])
class CommonProviderAutoConfiguration {

}