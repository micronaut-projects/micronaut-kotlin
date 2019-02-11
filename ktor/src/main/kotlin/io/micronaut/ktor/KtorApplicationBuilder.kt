package io.micronaut.ktor

import io.ktor.application.Application

/**
 * Allows declaring classes that can build Ktor Application instances
 *
 * @author graemerocher
 * @since 1.0
 */
abstract class KtorApplicationBuilder(val builder: Application.() -> Unit)