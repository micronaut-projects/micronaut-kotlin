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
package io.micronaut.kotlin.runtime

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.Micronaut

/**
 *  Top level function acting as a Kotlin shortcut allowing to write `startApplication<Foo>(args) { // Initializer function }`
 *  instead of `Micronaut.build(*args).mainClass(Foo::class.java).apply({ // Initializer }).start()`.
 *
 * @param T The application class
 * @param args The arguments
 * @param initializer Some function you want to apply to the build
 * @return The [ApplicationContext]
 * @author Will Buck
 * @since 2.3.1
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
 * @since 1.0.0
 */
inline fun <reified T> mnRun(vararg args: String): ApplicationContext = Micronaut.run(T::class.java, *args)

/**
 * Extension for [Micronaut.mainClass] providing a `mainClass<Foo>()` variant.
 *
 * @param T The main class
 * @return This builder
 * @author Alejandro Gomez
 * @since 1.0.0
 */
inline fun <reified T> Micronaut.mainClass(): Micronaut = mainClass(T::class.java)

/**
 * Extension for [Micronaut.mapError] providing a `mapError<FooException>(mapper)` variant.
 *
 * @param T The throwable error type
 * @param mapper the mapping function
 * @return The [Micronaut] entrypoint class
 * @author Will Buck
 * @since 2.3.1
 */
inline fun <reified T : Throwable> Micronaut.mapError(noinline mapper: (T) -> Int): Micronaut {
    return this.mapError(T::class.java, mapper)
}
