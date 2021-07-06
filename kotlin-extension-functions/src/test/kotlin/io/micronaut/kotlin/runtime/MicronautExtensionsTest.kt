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
package io.micronaut.kotlin.runtime


import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Parameter
import io.micronaut.context.annotation.Prototype
import io.micronaut.kotlin.context.getBean
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

/**
 * @author Alejandro Gomez
 */
class MicronautExtensionsTest {

    @Test
    fun mnRun() {
        val context = mnRun<TestFactory>("-baz=1")
        val foo = context.createBean(TestFactory.Foo::class.java, mapOf("baz" to 1))
        assertNotNull(foo)
        assertEquals(1, foo.baz)
        assertNotNull(context.getBean(TestFactory.Bar::class.java))
    }

    @Test
    fun mainClass() {
        val context = Micronaut.build().mainClass<TestFactory>().build().start()
        assertNotNull(context)
        assertNotNull(context.getBean<TestFactory.Bar>())
    }

    @Test
    fun mapError() {
        val customMappedErrorCode = 42
        val context = Micronaut.build().mapError<NullPointerException> { customMappedErrorCode }.build()
        assertNotNull(context)
    }

    @Factory
    class TestFactory {

        @Prototype
        class Foo(@Parameter("baz") val baz: Int)

        @Singleton
        class Bar
    }
}
