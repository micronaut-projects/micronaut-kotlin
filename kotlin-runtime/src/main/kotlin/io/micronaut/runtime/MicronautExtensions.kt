package io.micronaut.runtime

import io.micronaut.context.ApplicationContext

inline fun <reified T : Any> startApplication(vararg args: String, initializer: Micronaut.() -> Unit = {}): ApplicationContext {
    return Micronaut.build(*args)
        .mainClass(T::class.java)
        .apply(initializer)
        .start()
}

inline fun <reified T : Throwable> Micronaut.mapError(noinline mapper: (T) -> Int): Micronaut {
    return this.mapError(T::class.java, mapper)
}
