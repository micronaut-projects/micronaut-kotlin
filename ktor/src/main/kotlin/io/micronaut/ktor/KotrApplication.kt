package io.micronaut.ktor

import io.ktor.application.Application
import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironmentBuilder
import io.micronaut.runtime.Micronaut

abstract class KotrApplication<TConfiguration : ApplicationEngine.Configuration>(builder: Application.() -> Unit) {
    var environment : ApplicationEngineEnvironmentBuilder = ApplicationEngineEnvironmentBuilder()
    var configuration : TConfiguration.() -> Unit = {}

    init {
        this.environment.modules.add(builder)
    }

    fun applicationEngineEnvironment(builder: ApplicationEngineEnvironmentBuilder.() -> Unit): ApplicationEngineEnvironmentBuilder {
        return environment.apply(builder)
    }

    fun applicationEngine(builder: TConfiguration.() -> Unit): KotrApplication<TConfiguration> {
        configuration = builder
        return this
    }
}

fun runApplication(args: Array<String>) {
    Micronaut.run(*args)
}