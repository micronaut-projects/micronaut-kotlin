package io.micronaut.kotlin

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to the specified type.
 */
// tag::clientFunctionSingle[]
inline fun <reified T: Any> BlockingHttpClient.retrieveObject(request: HttpRequest<Any>): T =
        retrieve(request, Argument.of(T::class.java))
// end::clientFunctionSingle[]

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to a list of the specified type.
 */
// tag::clientFunctionList[]
inline fun <reified T: Any> BlockingHttpClient.retrieveList(request: HttpRequest<Any>): List<T> =
        retrieve(request, Argument.listOf(T::class.java))
// end::clientFunctionList[]