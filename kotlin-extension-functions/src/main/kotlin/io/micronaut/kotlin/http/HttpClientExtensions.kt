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
package io.micronaut.kotlin.http

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.client.BlockingHttpClient

/**
 * Shortcut to create an argument of the given type
 *
 * @param T The argument type
 * @return An [Argument<T>]
 * @author Will Buck
 * @since 1.0.0
 */
inline fun <reified T : Any> argumentOf(): Argument<T> = Argument.of(T::class.java)

/**
 * Shortcut to create an argument of a list the given type
 *
 * @param T The argument type
 * @return An [Argument<List<<T>>]
 * @author Will Buck
 * @since 1.0.0
 */
inline fun <reified T : Any> argumentOfList(): Argument<List<T>> = Argument.listOf(T::class.java)

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * [org.reactivestreams.Publisher] and converting the response body to the specified type. Allowing for
 * `client.exchangeObject<Hero>(HttpRequest.GET("/heroes/any"))` instead of
 * `client.exchange(HttpRequest.GET<Any>("/heroes/any"), Argument.of(Hero::class.java))`.
 *
 * @param T The response body type
 * @param request The [HttpRequest] you want to perform
 * @return The full [HttpResponse] with response body as an instance of [T]
 */
inline fun <reified T : Any> BlockingHttpClient.exchangeObject(request: HttpRequest<Any>): HttpResponse<T> =
    exchange(request, argumentOf<T>())

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to the specified type. Allows for
 * `client.retrieveObject<Hero>(HttpRequest.GET("/heroes/any"))` instead of
 * `client.retrieve(HttpRequest.GET<Any>("/heroes/any"), Argument.of(Hero::class.java))`.
 *
 * @param T The argument type
 * @param request The [HttpRequest] you want to perform
 * @return The response from the client as an instance of T
 * @author Will Buck
 * @since 1.0.0
 */
inline fun <reified T : Any> BlockingHttpClient.retrieveObject(request: HttpRequest<Any>): T =
    retrieve(request, argumentOf<T>())

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * [org.reactivestreams.Publisher] and converting the response body to a list of the specified type. Allowing for
 * `client.exchangeList<Hero>(HttpRequest.GET("/heroes/any"))` instead of
 * `client.exchange(HttpRequest.GET<Any>("/heroes/any"), Argument.listOf(Hero::class.java))`.
 *
 * @param T The response body type
 * @param request The [HttpRequest] you want to perform
 * @return The full [HttpResponse] with response body as an instance of [List<T>]
 */
inline fun <reified T : Any> BlockingHttpClient.exchangeList(request: HttpRequest<Any>): HttpResponse<List<T>> =
    exchange(request, argumentOfList<T>())

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to a list of the specified type. Allows for
 * `client.retrieveList<Hero>(HttpRequest.GET("/heroes/any"))` instead of
 * `client.retrieve(HttpRequest.GET<Any>("/heroes/any"), Argument.listOf(Hero::class.java))`.
 *
 * @param T The argument type
 * @param request The [HttpRequest] you want to perform
 * @return The response from the client as an instance of List<T>
 * @author Will Buck
 * @since 1.0.0
 */
inline fun <reified T : Any> BlockingHttpClient.retrieveList(request: HttpRequest<Any>): List<T> =
    retrieve(request, argumentOfList<T>())
