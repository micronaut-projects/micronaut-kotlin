package io.micronaut.context

import io.micronaut.inject.qualifierByStereotype
import java.util.stream.Stream
import kotlin.streams.asSequence

/**
 * Extension for [BeanLocator.getBean] providing a `getBean<Foo>()` variant.
 *
 * @param T The bean type
 * @return The bean instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanLocator.getBean(): T = getBean(T::class.java)

/**
 * Extension for [BeanLocator.getBean] providing a `getStereotypedBean<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The bean instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanLocator.getStereotypedBean(): T =
        getBean(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanLocator.findBean] providing a `findBean<Foo>()` variant.
 *
 * @param T The bean type
 * @return The bean instance or null
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanLocator.findBean(): T? = findBean(T::class.java).orElse(null)

/**
 * Extension for [BeanLocator.findBean] providing a `findStereotypedBean<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The bean instance or null
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanLocator.findStereotypedBean(): T? =
        findBean(T::class.java, qualifierByStereotype<T, Q>()).orElse(null)

/**
 * Extension for [BeanLocator.getBeansOfType] providing a `getBeansOfType<Foo>()` variant.
 *
 * @param T The bean type
 * @return A [Collection] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanLocator.getBeansOfType(): Collection<T> = getBeansOfType(T::class.java)

/**
 * Extension for [BeanLocator.getBeansOfType] providing a `getStereotypedBeansOfType<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return A [Collection] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanLocator.getStereotypedBeansOfType(): Collection<T> =
        getBeansOfType(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanLocator.streamOfType] providing a `streamOfType<Foo>()` variant.
 *
 * @param T The bean type
 * @return A [Stream] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanLocator.streamOfType(): Stream<T> = streamOfType(T::class.java)

/**
 * Extension for [BeanLocator.streamOfType] providing a `sequenceOfType<Foo>()` variant.
 *
 * @param T The bean type
 * @return A [Sequence] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanLocator.sequenceOfType(): Sequence<T> = streamOfType(T::class.java).asSequence()

/**
 * Extension for [BeanLocator.streamOfType] providing a `streamOfStereotypedType<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return A [Stream] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanLocator.streamOfStereotypedType(): Stream<T> =
        streamOfType(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanLocator.streamOfType] providing a `sequenceOfStereotypedType<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return A [Sequence] of bean instances
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanLocator.sequenceOfStereotypedType(): Sequence<T> =
        streamOfType(T::class.java, qualifierByStereotype<T, Q>()).asSequence()

/**
 * Extension for [BeanLocator.getProxyTargetBean] providing a `getProxyTargetBean<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The original bean instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanLocator.getProxyTargetBean(): T =
        getProxyTargetBean(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanLocator.findOrInstantiateBean] providing a `findOrInstantiateBean<Foo>()` variant.
 *
 * @param T The bean type
 * @return The bean instance or null
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanLocator.findOrInstantiateBean(): T? = findOrInstantiateBean(T::class.java).orElse(null)