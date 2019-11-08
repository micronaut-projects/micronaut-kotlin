package io.micronaut.jackson.kotlin

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Requires
import javax.inject.Singleton

/**
 * Automatically configures KotlinModule if module scan is disabled.
 *
 * @author Graeme Rocher
 * @since 1.0
 */
@Factory
class KotlinModuleFactory {

    @Requires(property = "jackson.module-scan", value = "false")
    @Singleton
    fun kotlinModuleFactory() : KotlinModule {
        return KotlinModule()
    }
}