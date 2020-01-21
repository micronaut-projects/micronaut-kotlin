package io.micronaut.kotlin

import io.micronaut.context.Qualifier
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
 * @since 0.0.1
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
 * @since 0.0.1
 */
inline fun <reified T, BT : BeanType<T>> Qualifier<T>.qualify(candidates: Stream<BT>): BT? = qualify(T::class.java, candidates).orElse(null)
