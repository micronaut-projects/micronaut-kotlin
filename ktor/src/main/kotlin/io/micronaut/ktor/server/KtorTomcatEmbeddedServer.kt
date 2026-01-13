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
package io.micronaut.ktor.server

import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.application.ApplicationEnvironment
import io.ktor.server.tomcat.jakarta.Tomcat
import io.ktor.server.tomcat.jakarta.TomcatApplicationEngine
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.core.io.socket.SocketUtils
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KtorApplication
import io.micronaut.ktor.KtorApplicationBuilder
import io.micronaut.ktor.KtorRoutingBuilder
import io.ktor.server.routing.routing
import jakarta.inject.Singleton

@Singleton
@Requires(classes = arrayOf(Tomcat::class))
class KtorTomcatEmbeddedServer(
    override val ctx: ApplicationContext,
    override val serverConfiguration: HttpServerConfiguration,
    override val engineEnvironment: ApplicationEnvironment,
    val ktorApplication: KtorApplication<TomcatApplicationEngine.Configuration>,
    val ktorApplicationBuilders: List<KtorApplicationBuilder>,
    val ktorRoutingBuilders: List<KtorRoutingBuilder>) : AbstractKtorEmbeddedServer(
    ctx,
    serverConfiguration,
    engineEnvironment,
    embeddedServer(
        Tomcat,
        environment = engineEnvironment,
        module = {
            ktorApplicationBuilders.forEach { it.builder(this) }
            ktorRoutingBuilders.forEach { routing { it.builder(this) } }
        },
        configure = {
            var specifiedPort = serverConfiguration.port.orElse(8080)
            if (specifiedPort == -1) {
                specifiedPort = SocketUtils.findAvailableTcpPort()
            }

            connectors.add(EngineConnectorBuilder().apply {
                host = serverConfiguration.host.orElse("0.0.0.0")
                port = specifiedPort
            })

            ktorApplication.configuration(this)
        }
    )
)
