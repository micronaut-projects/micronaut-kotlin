/*
 * Copyright 2017-2020 original authors
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

// tag::imports[]
import io.ktor.application.call
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.routing.*
import io.micronaut.ktor.KtorRoutingBuilder
import javax.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class GreetingRoutes(private val greetingService: GreetingService) : KtorRoutingBuilder({ // <1>
    get("/") {
        call.respond(greetingService.greet())
    }

    post("/") {
        val name = call.receive<CustomGreetingRequest>().name

        call.respond(greetingService.greet(name))
    }
})

data class CustomGreetingRequest(val name: String)
// end::class[]
