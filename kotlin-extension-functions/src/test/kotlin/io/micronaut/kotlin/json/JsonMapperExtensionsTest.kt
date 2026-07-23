/*
 * Copyright 2017-2026 original authors
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
package io.micronaut.kotlin.json

import io.micronaut.core.io.buffer.ReadBufferFactory
import io.micronaut.jackson.databind.JacksonDatabindMapper
import io.micronaut.json.JsonMapper
import io.micronaut.kotlin.http.argumentOf
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import tools.jackson.databind.ObjectMapper
import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets.UTF_8
import kotlin.test.assertEquals
import kotlin.test.assertNull

class JsonMapperExtensionsTest {

    private val jsonMapper: JsonMapper = JacksonDatabindMapper(ObjectMapper())

    open class TestData {
      var name: String? = null
      var value: Int = 0
    }

    @Nested
    inner class ReadValueFromTree {

        @Test
        fun shouldReturnDeserializedValueWhenTreeContainsValidData() {
            val testData = TestData().apply {
                name = "test"
                value = 42
            }
            val tree = jsonMapper.writeValueToTree(testData)

            val result = jsonMapper.readValueFromTree<TestData>(tree)!!

            assertEquals("test", result.name)
            assertEquals(42, result.value)
        }

        @Test
        fun shouldReturnNullWhenTreeIsNull() {
            val tree = jsonMapper.writeValueToTree(argumentOf<TestData>(), null)

            val result = jsonMapper.readValueFromTree<TestData>(tree)

            assertNull(result)
        }
    }

    @Nested
    inner class ReadValueFromInputStream {

        @Test
        fun shouldReturnDeserializedValueWhenStreamContainsValidJson() {
            val jsonString = """{"name":"test","value":42}"""
            val inputStream = ByteArrayInputStream(jsonString.toByteArray())

            val result = jsonMapper.readValue<TestData>(inputStream)!!

            assertEquals("test", result.name)
            assertEquals(42, result.value)
        }

        @Test
        fun shouldReturnNullWhenStreamContainsNull() {
            val jsonString = "null"
            val inputStream = ByteArrayInputStream(jsonString.toByteArray())

            val result = jsonMapper.readValue<TestData>(inputStream)

            assertNull(result)
        }
    }

    @Nested
    inner class ReadValueFromByteArray {

        @Test
        fun shouldReturnDeserializedValueWhenByteArrayContainsValidJson() {
            val jsonBytes = """{"name":"test","value":42}""".toByteArray()

            val result = jsonMapper.readValue<TestData>(jsonBytes)!!

            assertEquals("test", result.name)
            assertEquals(42, result.value)
        }

        @Test
        fun shouldReturnNullWhenByteArrayContainsNull() {
            val jsonBytes = "null".toByteArray()

            val result = jsonMapper.readValue<TestData>(jsonBytes)

            assertNull(result)
        }
    }

    @Nested
    inner class ReadValueFromReadBuffer {

        @Test
        fun shouldReturnDeserializedValueWhenByteBufferContainsValidJson() {
            val readBuffer = ReadBufferFactory.getJdkFactory()
                .copyOf("""{"name":"test","value":42}""", UTF_8)

            val result = jsonMapper.readValue<TestData>(readBuffer)!!

            assertEquals("test", result.name)
            assertEquals(42, result.value)
        }

        @Test
        fun shouldReturnNullWhenByteBufferContainsNull() {
            val readBuffer = ReadBufferFactory.getJdkFactory()
                .copyOf("null", UTF_8)

            val result = jsonMapper.readValue<TestData>(readBuffer)

            assertNull(result)
        }
    }

    @Nested
    inner class ReadValueFromString {

        @Test
        fun shouldReturnDeserializedValueWhenStringContainsValidJson() {
            val jsonString = """{"name":"test","value":42}"""

            val result = jsonMapper.readValue<TestData>(jsonString)!!

            assertEquals("test", result.name)
            assertEquals(42, result.value)
        }

        @Test
        fun shouldReturnNullWhenStringContainsNull() {
            val jsonString = "null"

            val result = jsonMapper.readValue<TestData>(jsonString)

            assertNull(result)
        }
    }
}
