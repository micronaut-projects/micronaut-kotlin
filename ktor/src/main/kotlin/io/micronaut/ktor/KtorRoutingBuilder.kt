package io.micronaut.ktor

import io.ktor.routing.Routing

/**
 * Allows declaring classes that can configure Ktor routes
 *
 * @author edrd-f
 * @since 1.0
 */
abstract class KtorRoutingBuilder(val builder: Routing.() -> Unit)
