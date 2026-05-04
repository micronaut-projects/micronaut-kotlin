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

import io.ktor.server.engine.ConnectorType
import io.ktor.server.engine.EngineConnectorConfig
import io.ktor.server.engine.EmbeddedServer
import io.micronaut.context.ApplicationContext
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.runtime.ApplicationConfiguration
import io.micronaut.runtime.server.EmbeddedServer as MicronautEmbeddedServer
import io.micronaut.runtime.server.event.ServerShutdownEvent
import io.micronaut.runtime.server.event.ServerStartupEvent
import java.net.URI
import java.net.URL
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Implementation of the EmbeddedServer interface for Ktor.
 *
 * @author graemerocher
 * @since 1.0
 */
abstract class AbstractKtorEmbeddedServer(
    open val ctx: ApplicationContext,
    open val serverConfiguration: HttpServerConfiguration,
    private val configuredConnector: EngineConnectorConfig,
    open val server: EmbeddedServer<*, *>,
) : MicronautEmbeddedServer {

    var running: AtomicBoolean = AtomicBoolean(false)
    private var resolvedConnector: EngineConnectorConfig = configuredConnector

    override fun getApplicationContext(): ApplicationContext {
        return ctx
    }

    override fun isRunning(): Boolean {
        return running.get()
    }

    override fun getURL(): URL {
        return uri.toURL()
    }

    override fun getHost(): String {
        return activeConnector().host
    }

    override fun getURI(): URI {
        return URI.create("$scheme://$host:$port")
    }

    override fun getApplicationConfiguration(): ApplicationConfiguration {
        return serverConfiguration.applicationConfiguration
    }

    override fun getPort(): Int {
        return activeConnector().port
    }

    override fun getScheme(): String {
        return when (activeConnector().type) {
            ConnectorType.HTTP -> "http"
            ConnectorType.HTTPS -> "https"
            else -> activeConnector().type.name.lowercase(Locale.ENGLISH)
        }.lowercase(Locale.ENGLISH)
    }

    override fun start(): MicronautEmbeddedServer {
        if (running.compareAndSet(false, true)) {
            server.start(false)
            resolvedConnector = runCatching {
                kotlinx.coroutines.runBlocking { server.engine.resolvedConnectors() }.firstOrNull() ?: configuredConnector
            }.getOrElse { configuredConnector }
            ctx.publishEvent(ServerStartupEvent(this))
        }
        return this
    }

    override fun stop(): MicronautEmbeddedServer {
        if (running.compareAndSet(true, false)) {
            server.stop(1000, 5000)
            ctx.publishEvent(ServerShutdownEvent(this))
        }
        return this
    }

    private fun activeConnector(): EngineConnectorConfig = if (running.get()) resolvedConnector else configuredConnector
}
