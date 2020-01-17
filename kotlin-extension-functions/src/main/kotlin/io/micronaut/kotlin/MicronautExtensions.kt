package io.micronaut.kotlin

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.Micronaut

// tag:startApplication
inline fun <reified T : Any> startApplication(vararg args: String, initializer: Micronaut.() -> Unit = {}): ApplicationContext {
    return Micronaut.build(*args)
            .mainClass(T::class.java)
            .apply(initializer)
            .start()
}
// end:startApplication

inline fun <reified T : Throwable> Micronaut.mapError(noinline mapper: (T) -> Int): Micronaut {
    return this.mapError(T::class.java, mapper)
}