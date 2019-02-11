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
package io.micronaut.ktor.server

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.ktor.server.jetty.Jetty
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KtorApplication
import javax.inject.Singleton

@Singleton
@Requires(classes = arrayOf(Jetty::class))
class KtorJettyEmbeddedServer(
        override val ctx: ApplicationContext,
        override val serverConfiguration: HttpServerConfiguration,
        override val engineEnvironment: ApplicationEngineEnvironment,
        val ktorApplication: KtorApplication<ApplicationEngine.Configuration>) : AbstractKtorEmbeddedServer(
        ctx,
        serverConfiguration,
        engineEnvironment,
        Jetty.create(engineEnvironment, ktorApplication.configuration)
)