package app

import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.basic
import io.micronaut.ktor.KtorApplicationBuilder
import javax.inject.Singleton

@Singleton
class AuthenticationBuilder : KtorApplicationBuilder({
    install(Authentication) {
        basic {
            validate { null }
        }
    }
})
