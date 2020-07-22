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
package io.micronaut.inject

import io.micronaut.context.Qualifier
import io.micronaut.core.annotation.AnnotationMetadata
import io.micronaut.inject.qualifiers.Qualifiers

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `qualifierByAnnotation<Foo, Bar>(metadata)`
 *  instead of `Qualifiers.byAnnotation(metadata, Bar::class.java)`.
 *
 * @param T The component type
 * @param Q The stereotype type
 * @param metadata The metadata
 * @return The [Qualifier]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> qualifierByAnnotation(metadata: AnnotationMetadata): Qualifier<T> =
        Qualifiers.byAnnotation(metadata, Q::class.java)

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `qualifierByStereotype<Foo, Bar>()`
 *  instead of `Qualifiers.byStereotype(Bar::class.java)`.
 *
 * @param T The component type
 * @param Q The stereotype type
 * @return The [Qualifier]
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> qualifierByStereotype(): Qualifier<T> = Qualifiers.byStereotype(Q::class.java)