package com.example.api.provider.jpa.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.hypersistence.utils.hibernate.type.util.ObjectMapperSupplier
import net.sunshow.toolkit.core.qbean.helper.repository.BaseRepositoryFactoryBean
import nxcloud.foundation.core.spring.support.SpringContextHelper
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * author: sunshow.
 */
@Configuration
@EnableJpaRepositories(
    repositoryFactoryBeanClass = BaseRepositoryFactoryBean::class,
    basePackages = ["**.provider.*.repository.database.*"]
)
@EntityScan(basePackages = ["**.provider.*.repository.database.*", "net.sunshow.toolkit.core.base.enums.converter"])
class CommonJpaConfig {

    /**
     * 默认使用数据库自增字段作为主键
     */
    @Bean
    fun identifierGeneratorStrategyHibernatePropertiesCustomizer(): HibernatePropertiesCustomizer {
        return HibernatePropertiesCustomizer {
            it["hibernate.identifier_generator_strategy_provider"] =
                "nxcloud.foundation.core.data.jpa.id.IdentityIdentifierGeneratorStrategyProvider"
        }
    }

}

class HibernateObjectMapperSupplier : ObjectMapperSupplier {

    private val objectMapper by lazy {
        SpringContextHelper.getBean(ObjectMapper::class.java)
    }

    override fun get(): ObjectMapper {
        return objectMapper
    }

}