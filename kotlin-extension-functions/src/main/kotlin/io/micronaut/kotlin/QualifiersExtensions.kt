package io.micronaut.kotlin

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
 * @since 0.0.1
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
 * @since 0.0.1
 */
inline fun <reified T, reified Q : Annotation> qualifierByStereotype(): Qualifier<T> = Qualifiers.byStereotype(Q::class.java)