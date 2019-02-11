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

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.KtorApplication
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class App(val greetingService: GreetingService) : KtorApplication<NettyApplicationEngine.Configuration>({
    routing {
        get("/") {
            call.respondText(greetingService.greet(), ContentType.Text.Plain)
        }
        get("/demo") {
            call.respondText(greetingService.greet())
        }
    }
}) {
    init {
        applicationEngineEnvironment {
            log = LoggerFactory.getLogger(App::class.java)
        }

        applicationEngine {
            workerGroupSize = 10
        }
    }
}