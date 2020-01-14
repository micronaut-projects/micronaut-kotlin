package io.micronaut.kotlin

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.BlockingHttpClient

inline fun <reified T: Any> BlockingHttpClient.retrieve(request: HttpRequest<Any>): T =
        retrieve(request, Argument.of(T::class.java))

inline fun <reified T: Any> BlockingHttpClient.retrieveList(request: HttpRequest<Any>): List<T> =
        retrieve(request, Argument.listOf(T::class.java))