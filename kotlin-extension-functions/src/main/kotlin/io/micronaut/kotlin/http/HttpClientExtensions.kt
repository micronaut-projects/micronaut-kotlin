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
import io.micronaut.http.client.BlockingHttpClient

/**
 * Shortcut to create an argument of the given type
 *
 * @param T The argument type
 * @return An [Argument<T>]
 * @author Will Buck
 * @since 1.0.0
 */
inline fun <reified T: Any> argumentOf(): Argument<T> = Argument.of(T::class.java)

/**
 * Shortcut to create an argument of a list the given type
 *
 * @param T The argument type
 * @return An [Argument<List<<T>>]
 * @author Will Buck
 * @since 1.0.0
 */
inline fun <reified T: Any> argumentOfList(): Argument<List<T>> = Argument.listOf(T::class.java)

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to the specified type.
 *
 * @param T The argument type
 * @param request The [HttpRequest] you want to perform
 * @return The response from the client as an instance of T
 * @author Will Buck
 * @since 1.0.0
 */
// tag::clientFunctionSingle[]
inline fun <reified T: Any> BlockingHttpClient.retrieveObject(request: HttpRequest<Any>): T =
        retrieve(request, argumentOf<T>())
// end::clientFunctionSingle[]

/**
 * Perform an HTTP request for the given request object emitting the full HTTP response from returned
 * Publisher and converting the response body to a list of the specified type.
 *
 * @param T The argument type
 * @param request The [HttpRequest] you want to perform
 * @return The response from the client as an instance of List<T>
 * @author Will Buck
 * @since 1.0.0
 */
// tag::clientFunctionList[]
inline fun <reified T: Any> BlockingHttpClient.retrieveList(request: HttpRequest<Any>): List<T> =
        retrieve(request, argumentOfList<T>())
// end::clientFunctionList[]
