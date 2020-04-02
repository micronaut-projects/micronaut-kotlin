package io.micronaut.http

import io.micronaut.core.type.Argument
import io.micronaut.http.client.BlockingHttpClient

/**
 * Shortcut to create an argument of the given type
 */
inline fun <reified T: Any> argumentOf(): Argument<T> = Argument.of(T::class.java)

/**
 * Shortcut to create an argument of a list of the given type
 */
inline fun <reified T: Any> argumentOfList(): Argument<List<T>> = Argument.listOf(T::class.java)

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to the specified type.
 */
// tag::clientFunctionSingle[]
inline fun <reified T: Any> BlockingHttpClient.retrieveObject(request: HttpRequest<Any>): T =
        retrieve(request, argumentOf<T>())
// end::clientFunctionSingle[]

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to a list of the specified type.
 */
// tag::clientFunctionList[]
inline fun <reified T: Any> BlockingHttpClient.retrieveList(request: HttpRequest<Any>): List<T> =
        retrieve(request, argumentOfList<T>())
// end::clientFunctionList[]