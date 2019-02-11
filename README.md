# Micronaut Kotlin

This project contains a collection of subprojects that improve Micronaut with Kotlin

## Micronaut Kotlin Runtime

The `micronaut-kotlin-runtime` dependency adds a few features to specific to Kotlin including:

* Support for [HOCON](https://github.com/lightbend/config/blob/master/HOCON.md) properties files (example `src/main/resources/application.conf`)
* The necessary [Jackson dependencies](https://github.com/FasterXML/jackson-module-kotlin)

This module can be used in Micronaut or Ktor applications (see below)

## Micronaut Ktor


The [ktor](https://ktor.io) subproject adds support for the kotr webserver. 


### Using the Snapshot (`build.gradle`):

To use the snapshot configure the following in your build (see the [examples/greeting](https://github.com/micronaut-projects/micronaut-kotlin/tree/master/examples/greeting) directory for an example).

### Example

Once you have configured Ktor integration you typically define routes in one or means beans that are dependency injected with other components:

```kotlin
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.KtorApplication
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class App(val greetingService: GreetingService) : KotrApplication<NettyApplicationEngine.Configuration>({
    routing {
        get("/") {
            call.respondText(greetingService.greet(), ContentType.Text.Plain)
        }
        get("/demo") {
            call.respondText(greetingService.greet())
        }
    }
}) {
    init {
        applicationEngineEnvironment {
            log = LoggerFactory.getLogger(App::class.java)
        }

        applicationEngine {
            workerGroupSize = 10
        }
    }
}
```
