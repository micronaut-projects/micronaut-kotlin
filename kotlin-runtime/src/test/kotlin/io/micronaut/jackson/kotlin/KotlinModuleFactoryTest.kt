package io.micronaut.jackson.kotlin

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.micronaut.context.ApplicationContext
import org.junit.jupiter.api.Test

class KotlinModuleFactoryTest {

    @Test
    fun testModuleScanEnabled() {
        ApplicationContext.run().use {
            val objectMapper = it.getBean(ObjectMapper::class.java)
            val moduleIds = objectMapper.registeredModuleIds
            // found via module scan
            assert(moduleIds.contains(KotlinModule::class.java.name))
            assert(!it.containsBean(KotlinModule::class.java))
        }
    }

    @Test
    fun testModuleScanDisabled() {
        ApplicationContext.run(
                mapOf("jackson.module-scan" to false)
        ).use {
            val objectMapper = it.getBean(ObjectMapper::class.java)
            val moduleIds = objectMapper.registeredModuleIds
            // found by bean context creation
            assert(moduleIds.contains(KotlinModule::class.java.name))
            assert(it.containsBean(KotlinModule::class.java))
        }
    }
}