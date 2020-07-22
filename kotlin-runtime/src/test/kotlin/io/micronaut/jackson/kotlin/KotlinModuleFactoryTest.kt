/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

}