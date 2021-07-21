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

import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux

@MicronautTest
class GreetingTest {

    @Inject
    lateinit var client: GreetingClient

    @Test
    fun testHelloGet() {
        assertEquals(
            "Hello World",
            Flux.from(client.hello()).blockFirst()!!.message
        )
    }

    @Test
    fun testHelloPost() {
        assertEquals(
            "Hello Micronaut",
            Flux.from(client.hello(name = "Micronaut")).blockFirst()!!.message
        )
    }
}

@Client("/")
interface GreetingClient {

    @Get("/")
    fun hello(): Publisher<Greeting>

    @Post("/")
    fun hello(name: String): Publisher<Greeting>
}
