package io.micronaut.context

import io.micronaut.inject.qualifierByStereotype

/**
 * Extension for [BeanContext.createBean] providing a `createBean<Foo>()` variant.
 *
 * @param T The bean type
 * @return The instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanContext.createBean(): T = createBean(T::class.java)

/**
 * Extension for [BeanContext.createBean] providing a `createStereotypedBean<Foo, Bar>()` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @return The instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanContext.createStereotypedBean(): T = createBean(T::class.java, qualifierByStereotype<T, Q>())

/**
 * Extension for [BeanContext.createBean] providing a `createStereotypedBean<Foo, Bar>(args)` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @param argumentValues The argument values
 * @return The instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanContext.createStereotypedBean(argumentValues: Map<String, Any>): T =
        createBean(T::class.java, qualifierByStereotype<T, Q>(), argumentValues)

/**
 * Extension for [BeanContext.createBean] providing a `createStereotypedBean<Foo, Bar>(args)` variant.
 *
 * @param T The bean type
 * @param Q The stereotype type
 * @param args The argument values
 * @return The instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T, reified Q : Annotation> BeanContext.createStereotypedBean(vararg args: Any): T =
        createBean(T::class.java, qualifierByStereotype<T, Q>(), *args)

/**
 * Extension for [BeanContext.createBean] providing a `createBean<Foo>(args)` variant.
 *
 * @param T The bean type
 * @param argumentValues The argument values
 * @return The instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanContext.createBean(argumentValues: Map<String, Any>): T = createBean(T::class.java, argumentValues)

/**
 * Extension for [BeanContext.createBean] providing a `createBean<Foo>(args)` variant.
 *
 * @param T The bean type
 * @param args The argument values
 * @return The instance
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanContext.createBean(vararg args: Any): T = createBean(T::class.java, *args)

/**
 * Extension for [BeanContext.destroyBean] providing a `destroyBean<Foo>()` variant.
 *
 * @param T The bean type
 * @return The destroy instance or null if no such bean exists
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> BeanContext.destroyBean(): T? = destroyBean(T::class.java)