/*
 * Copyright 2017-2025 original authors
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

import io.micronaut.http.HttpMessage
import kotlin.jvm.optionals.getOrNull

/**
 * Extension property to retrieve the body of the [HttpMessage] as a nullable object instead of an `Optional`.
 * Allowing to write `message.bodyOrNull` instead of `message.body.getOrNull()`.
 *
 * @param B The type of the body
 * @return The body of the message as a nullable object, or null if the body is not present
 */
val <B : Any> HttpMessage<B>.bodyOrNull: B?
    get() = this.body.getOrNull()


/**
 * Retrieve the body of the message as an object of the given type. Allowing to write `message.getBodyObject<Foo>()`
 * instead of `message.getBody(Argument.of(Foo::class.java)).getOrNull()` or `message.getBody(Foo::class.java)).getOrNull()`.
 *
 * @param T The type of the object
 */
inline fun <reified T : Any> HttpMessage<*>.getBodyObject(): T? = this.getBody(argumentOf<T>()).getOrNull()

