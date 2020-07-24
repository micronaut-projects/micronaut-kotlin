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
package io.micronaut.context.env.hocon

import io.micronaut.context.env.DefaultEnvironment
import io.micronaut.runtime.Micronaut
import io.micronaut.context.ApplicationContext
import org.junit.jupiter.api.Test

class HoconPropertySourceLoaderTest {

    @Test
    fun testPropertySourceLoader() {
        val env = DefaultEnvironment(Micronaut.build())
        env.start()
        ApplicationContext.run().use {
            val value = it.getProperty("micronaut.server.port", Integer::class.java)
            assert(
                    value.get().toInt() == 8081
            )
        }
    }

    @Test
    fun testPropertySourceLoaderOrder() {
        System.setProperty("test-property", "good value")
        val env = DefaultEnvironment(Micronaut.build())
        env.start()
        ApplicationContext.run().use {
            val value = it.getProperty("test-property", String::class.java)
            assert(
                    value.get() == "good value"
            )
        }
        System.clearProperty("test-property")
    }

    @Test
    fun testPropertySourceLoaderEnvironmentVariable() {
        val env = DefaultEnvironment(Micronaut.build())
        env.start()
        ApplicationContext.run().use {
            val value = it.getProperty("custom.user", String::class.java)
            assert(
                    value.get() == System.getProperty("user.name")
            )
        }
    }

    @Test
    fun testExternalPropertySourceLoader() {
        System.setProperty("micronaut.config.files", "classpath:config_file.conf")
        val env = DefaultEnvironment(Micronaut.build())
        env.start()
        ApplicationContext.run().use {
            val value = it.getProperty("micronaut.server.port", Integer::class.java)
            assert(
                    value.get().toInt() == 8082
            )
        }
        System.clearProperty("micronaut.config.files")
    }

}