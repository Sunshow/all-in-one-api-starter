package com.example.api.gateway

import nxcloud.ext.springmvc.automapping.annotation.NXEnableSpringMvcAutoMapping
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * author: sunshow.
 */

@NXEnableSpringMvcAutoMapping
@SpringBootApplication
class ExampleApiGatewayApplication

fun main(args: Array<String>) {
    runApplication<ExampleApiGatewayApplication>(*args)
}