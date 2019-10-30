/*
 * Copyright 2017-2019 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
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
import io.micronaut.test.annotation.MicronautTest
import io.reactivex.Single
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import javax.inject.Inject

@MicronautTest
class GreetingTest {
    @Inject
    lateinit var client: GreetingClient

    @Test
    fun testHelloGet() {
        assertEquals(
            "Hello World",
            client.hello().blockingGet().message
        )
    }

    @Test
    fun testHelloPost() {
        assertEquals(
            "Hello Micronaut",
            client.hello(name = "Micronaut").blockingGet().message
        )
    }
}

@Client("/")
interface GreetingClient {
    @Get("/")
    fun hello(): Single<Greeting>

    @Post("/")
    fun hello(name: String): Single<Greeting>
}
