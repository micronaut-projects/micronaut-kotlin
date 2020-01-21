package io.micronaut.kotlin

import io.micronaut.context.ApplicationContext
import io.micronaut.context.env.PropertySource

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `run<Foo>("env")`
 *  instead of `ApplicationContext.run(Foo::class.java, "env")`.
 *
 * @param T The type
 * @param environments The environments to use
 * @return The running bean
 * @author Alejandro Gomez
 * @since 0.0.1
 */
inline fun <reified T : AutoCloseable> run(vararg environments: String): T = ApplicationContext.run(T::class.java, *environments)

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `run<Foo>(props, "env")`
 *  instead of `ApplicationContext.run(Foo::class.java, props, "env")`.
 *
 * @param T The type
 * @param properties Additional properties
 * @param environments The environments to use
 * @return The running bean
 * @author Alejandro Gomez
 * @since 0.0.1
 */
inline fun <reified T : AutoCloseable> run(properties: Map<String, Any?>, vararg environments: String): T =
        ApplicationContext.run(T::class.java, properties, *environments)

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `run<Foo>(props, "env")`
 *  instead of `ApplicationContext.run(Foo::class.java, props, "env")`.
 *
 * @param T The type
 * @param propertySource Additional properties
 * @param environments The environments to use
 * @return The running bean
 * @author Alejandro Gomez
 * @since 0.0.1
 */
inline fun <reified T : AutoCloseable> run(propertySource: PropertySource, vararg environments: String): T =
        ApplicationContext.run(T::class.java, propertySource, *environments)

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `buildAndStart<Foo>(mapOf("foo" to "bar"))`
 *  instead of `ApplicationContext.build(Foo::class.java)propertySources(propSource).start()`.
 *
 * @param T The type
 * @return The running [ApplicationContext]
 * @author Alejandro Gomez
 * @since 0.0.2
 */
inline fun <reified T> buildAndStart(): ApplicationContext = ApplicationContext.build(T::class.java).start()

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `buildAndStart<Foo>(mapOf("foo" to "bar"))`
 *  instead of `ApplicationContext.build(Foo::class.java)propertySources(propSource).start()`.
 *
 * @param T The type
 * @param propertySources The property sources to include
 * @return The running [ApplicationContext]
 * @author Alejandro Gomez
 * @since 0.0.2
 */
inline fun <reified T> buildAndStart(vararg propertySources: PropertySource): ApplicationContext =
        ApplicationContext.build(T::class.java).propertySources(*propertySources).start()

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `buildAndStart<Foo>("env")`
 *  instead of `ApplicationContext.build(Foo::class.java, "env").start()`.
 *
 * @param T The type
 * @param environments The environments to use
 * @return The running [ApplicationContext]
 * @author Alejandro Gomez
 * @since 0.0.2
 */
inline fun <reified T> buildAndStart(vararg environments: String): ApplicationContext = ApplicationContext.build(T::class.java, *environments).start()

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `buildAndStart<Foo>("env")`
 *  instead of `ApplicationContext.build(mapOf("foo" to "bar"), "env").mainClass(Foo::class.java).start()`.
 *
 * @param T The type
 * @param properties Additional properties
 * @param environments The environments to use
 * @return The running [ApplicationContext]
 * @author Alejandro Gomez
 * @since 0.0.2
 */
inline fun <reified T> buildAndStart(properties: Map<String, Any?>, vararg environments: String): ApplicationContext =
        ApplicationContext.build(properties, *environments).mainClass(T::class.java).start()

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `buildAndStart<Foo>("env")`
 *  instead of `ApplicationContext.build(Foo::class.java, "env").propertySources(propSource).start()`.
 *
 * @param T The type
 * @param propertySource The property source to include
 * @param environments The environments to use
 * @return The running [ApplicationContext]
 * @author Alejandro Gomez
 * @since 0.0.2
 */
inline fun <reified T> buildAndStart(propertySource: PropertySource, vararg environments: String): ApplicationContext =
        ApplicationContext.build(T::class.java, *environments).propertySources(propertySource).start()
