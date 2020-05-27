/*
 * Copyright 2017-2020 original authors
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
package io.micronaut.context

import io.micronaut.inject.BeanType
import java.util.stream.Stream

/**
 * Extension for [Qualifier.reduce] providing a `reduce<Foo>(candidates)` variant.
 *
 * @param T The bean type
 * @param BT The bean type subclass
 * @param candidates The candidates
 * @return A [Stream] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, BT : BeanType<T>> Qualifier<T>.reduce(candidates: Stream<BT>): Stream<BT> = reduce(T::class.java, candidates)

/**
 * Extension for [Qualifier.qualify] providing a `qualify<Foo>(candidates)` variant.
 *
 * @param T The bean type
 * @param BT The bean type subclass
 * @param candidates The candidates
 * @return The qualified candidate or null
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, BT : BeanType<T>> Qualifier<T>.qualify(candidates: Stream<BT>): BT? = qualify(T::class.java, candidates).orElse(null)
