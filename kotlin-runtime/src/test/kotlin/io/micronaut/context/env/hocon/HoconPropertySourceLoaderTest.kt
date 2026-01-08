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

import io.micronaut.context.ApplicationContext
import org.junit.jupiter.api.Test

class HoconPropertySourceLoaderTest {

    @Test
    fun testPropertySourceLoader() {
        ApplicationContext.run(mapOf("micronaut.server.port" to 8081)).use { context ->
            val value = context.getProperty("micronaut.server.port", Int::class.java)

            assert(value.orElse(0) == 8081)
        }
    }

    @Test
    fun testPropertySourceLoaderOrder() {
        System.setProperty("test-property", "good value")
            ApplicationContext.run().use { context ->
                val value = context.getProperty("test-property", String::class.java)

                assert(value.isPresent)
                assert(value.get() == "good value")
            }
    }

    @Test
    fun testPropertySourceLoaderEnvironmentVariable() {
        ApplicationContext.builder().start().use { context ->
            val value = context.getProperty("custom.user", String::class.java)

            assert(value.isPresent)
            assert(value.get() == System.getProperty("user.name"))
        }
    }

    @Test
    fun testExternalPropertySourceLoader() {
        System.setProperty("micronaut.config.files", "classpath:config_file.conf")
            ApplicationContext.run().use { context ->
                val value = context.getProperty("micronaut.server.port", Int::class.java)

                assert(value.isPresent)
                assert(value.get() == 8082)
            }
    }

}
