package example.micronaut

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.annotation.ReflectiveAccess

@ReflectiveAccess
@Introspected
data class Greeting(val message: String)
