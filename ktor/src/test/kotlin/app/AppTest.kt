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
package app

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.http.client.exceptions.HttpClientResponseException
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.assertThrows

@MicronautTest
class AppTest {

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun testApp() {
        assertEquals(
            "Hello World",
            client.toBlocking().retrieve("/demo")
        )
    }

    @Test
    fun testFeatureDependentRoute() {
        val exception = assertThrows<HttpClientResponseException>() {
            client.toBlocking().retrieve("/authenticated")
        }

        assertEquals("Unauthorized", exception.message)
    }
}
