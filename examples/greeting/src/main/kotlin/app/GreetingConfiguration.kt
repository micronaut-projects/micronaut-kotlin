package app

// tag::imports[]
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.jackson.jackson
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton
// end::imports[]

// tag::class[]
@Singleton
class GreetingConfiguration : KtorApplicationBuilder({ // <1>
    install(ContentNegotiation) { // <2>
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
})
// end::class[]
