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
package io.micronaut.ktor

import io.ktor.server.engine.ApplicationEngine
import io.ktor.server.engine.ApplicationEngineEnvironmentBuilder
import io.micronaut.runtime.Micronaut

/**
 * Allows configuring the Ktor application engine.
 *
 * @author
 * @since 1.0
 */
abstract class KtorApplication<TConfiguration : ApplicationEngine.Configuration>(val builder: KtorApplication<TConfiguration>.() -> Unit) {

    var environment : ApplicationEngineEnvironmentBuilder = ApplicationEngineEnvironmentBuilder()
    var configuration : TConfiguration.() -> Unit = {}

    fun applicationEngineEnvironment(builder: ApplicationEngineEnvironmentBuilder.() -> Unit): ApplicationEngineEnvironmentBuilder {
        return environment.apply(builder)
    }

    fun applicationEngine(builder: TConfiguration.() -> Unit): KtorApplication<TConfiguration> {
        configuration = builder
        return this
    }

    fun init() {
        builder(this)
    }
}

/**
 * Runs a Micronaut application.
 */
fun runApplication(args: Array<String>) {
    Micronaut.run(*args)
}