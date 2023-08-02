package example.micronaut

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Greeting(val message: String)
