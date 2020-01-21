package io.micronaut.kotlin

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.Micronaut

/**
 *
 */
// tag:startApplication
inline fun <reified T : Any> startApplication(vararg args: String, initializer: Micronaut.() -> Unit = {}): ApplicationContext {
    return Micronaut.build(*args)
            .mainClass(T::class.java)
            .apply(initializer)
            .start()
}
// end:startApplication

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `mnRun<Foo>(args)`
 *  instead of `Micronaut.run(Foo::class.java, *args)`.
 *
 * @param T The application class
 * @param args The arguments
 * @return The [ApplicationContext]
 * @author Alejandro Gomez
 * @since 0.0.3
 */
inline fun <reified T> mnRun(vararg args: String): ApplicationContext = Micronaut.run(T::class.java, *args)

/**
 * Extension for [Micronaut.mainClass] providing a `mainClass<Foo>()` variant.
 *
 * @param T The main class
 * @return This builder
 * @author Alejandro Gomez
 * @since 0.0.9
 */
inline fun <reified T> Micronaut.mainClass(): Micronaut = mainClass(T::class.java)

/**
 * Extension for [Micronaut.mapError] providing a `mapError<FooException>(mapper)` variant.
 */
inline fun <reified T : Throwable> Micronaut.mapError(noinline mapper: (T) -> Int): Micronaut {
    return this.mapError(T::class.java, mapper)
}