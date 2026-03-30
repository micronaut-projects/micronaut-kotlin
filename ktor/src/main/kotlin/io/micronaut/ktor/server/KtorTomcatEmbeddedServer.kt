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

import io.ktor.server.application.ServerConfig
import io.ktor.server.engine.EngineConnectorConfig
import io.ktor.server.engine.embeddedServer
import io.ktor.server.tomcat.jakarta.Tomcat
import io.ktor.server.tomcat.jakarta.TomcatApplicationEngine
import io.micronaut.context.ApplicationContext
import io.micronaut.context.annotation.Requires
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.ktor.KtorApplication
import jakarta.inject.Singleton

@Singleton
@Requires(classes = [Tomcat::class])
class KtorTomcatEmbeddedServer(
    override val ctx: ApplicationContext,
    override val serverConfiguration: HttpServerConfiguration,
    serverConfig: ServerConfig,
    connectorConfigs: List<EngineConnectorConfig>,
    val ktorApplication: KtorApplication<TomcatApplicationEngine.Configuration>,
) : AbstractKtorEmbeddedServer(
    ctx,
    serverConfiguration,
    connectorConfigs.first(),
    embeddedServer(Tomcat, serverConfig, configure = {
        ktorApplication.configuration(this)
        connectors = connectorConfigs.toMutableList()
    }),
)
