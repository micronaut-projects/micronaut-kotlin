package io.micronaut.ktor.server

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironment
import io.micronaut.context.ApplicationContext
import io.micronaut.http.server.HttpServerConfiguration
import io.micronaut.runtime.ApplicationConfiguration
import io.micronaut.runtime.server.EmbeddedServer
import java.net.URI
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

abstract class AbstractKotrEmbeddedServer(
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

        return first.type.name.toLowerCase(Locale.ENGLISH)
    }

    override fun start(): EmbeddedServer {
        if (running.compareAndSet(false, true)) {
            applicationEngine.start()
        }
        return this
    }

    override fun stop(): EmbeddedServer {
        if (running.compareAndSet(true, false)) {
            applicationEngine.stop(1, 5, TimeUnit.SECONDS)
        }
        return this
    }
}