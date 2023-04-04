package com.example.api.domain.common.usecase

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import javax.validation.ConstraintViolationException
import javax.validation.Validator

@Validated // 单纯为了让 Spring 增强一下好让 execute 执行时可以拿到实际实现类的 InputData 类型
abstract class AbstractUseCase<Input : AbstractUseCase.InputData, Output : AbstractUseCase.OutputData> {

    protected val logger = KotlinLogging.logger {}

    @Autowired(required = false)
    private var validator: Validator? = null

    fun execute(input: Input): Output {
        validator?.validate(input)
            ?.takeIf {
                it.isNotEmpty()
            }
            ?.let {
                throw ConstraintViolationException(it)
            }
        return doAction(input)
    }

    protected abstract fun doAction(input: Input): Output

    abstract class InputData

    abstract class OutputData

}