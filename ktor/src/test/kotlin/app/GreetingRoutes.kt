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

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.micronaut.ktor.KtorRoutingBuilder
import javax.inject.Singleton

@Singleton
class GreetingRoutes(private val greetingService: GreetingService) : KtorRoutingBuilder({
    authenticate {
        get("/authenticated") {
            call.respondText("Hello from authenticated route")
        }
    }

    get("/") {
        call.respondText(greetingService.greet(), ContentType.Text.Plain)
    }

    get("/demo") {
        call.respondText(greetingService.greet())
    }
})
