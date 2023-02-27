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

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.micronaut.context.ApplicationContext
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.runtime.ApplicationConfiguration
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.runtime.server.event.ServerShutdownEvent
import io.micronaut.runtime.server.event.ServerStartupEvent
import java.net.URI
import java.net.URL
import java.util.*
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
        open val engineEnvironment: ApplicationEngineEnvironment,
        open val applicationEngine: ApplicationEngine) : EmbeddedServer {

    var running : AtomicBoolean = AtomicBoolean(false)

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
        val connectors = engineEnvironment.connectors
        val first = connectors.first()
        return first.host
    }

    override fun getURI(): URI {
        return URI.create("$scheme://$host:$port")
    }

    override fun getApplicationConfiguration(): ApplicationConfiguration {
        return serverConfiguration.applicationConfiguration
    }

    override fun getPort(): Int {
        val connectors = engineEnvironment.connectors
        val first = connectors.first()
        return first.port
    }

    override fun getScheme(): String {
        val connectors = engineEnvironment.connectors
        val first = connectors.first()

        return first.type.name.lowercase(Locale.ENGLISH)
    }

    override fun start(): EmbeddedServer {
        if (running.compareAndSet(false, true)) {
            applicationEngine.start()
            ctx.publishEvent(ServerStartupEvent(this))
        }
        return this
    }

    override fun stop(): EmbeddedServer {
        if (running.compareAndSet(true, false)) {
            applicationEngine.stop(1000, 5000)
            ctx.publishEvent(ServerShutdownEvent(this))
        }
        return this
    }

    override fun isKeepAlive(): Boolean {
        return false
    }
}
