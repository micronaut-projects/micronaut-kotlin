package example.micronaut

import io.micronaut.core.annotation.Introspected

@Introspected
data class Greeting(val message: String)
