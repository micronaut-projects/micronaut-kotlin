# Micronaut Kotlin

This project contains a collection of subprojects that improve Micronaut with Kotlin


## Micronaut Ktor



The [ktor](https://ktor.io) subproject adds support for the kotr webserver. 


### Using the Snapshot (`build.gradle`):

To use the snapshot configure the following in your build (see the "examples/greeting" directory for an example).

```groovy

repositories {
    mavenCentral()
    jcenter()
    maven { url "https://oss.sonatype.org/content/repositories/snapshots/"}
    maven { url "https://dl.bintray.com/kotlin/ktor" }
    maven { url "https://dl.bintray.com/kotlin/kotlin-eap" }
}
dependencyManagement {
    imports {
        mavenBom 'io.micronaut:micronaut-bom:1.0.0.RC3'
    }
}
dependencies {
    annotationProcessor "io.micronaut:micronaut-inject-java:$micronautVersion"
    kapt "io.micronaut:micronaut-inject-java:$micronautVersion"
    kaptTest "io.micronaut:micronaut-inject-java:$micronautVersion"

    compile "io.micronaut.kotlin:micronaut-ktor:1.0.0.BUILD-SNAPSHOT"
    compile "io.ktor:ktor-server-netty:$ktor_version"
    runtime "ch.qos.logback:logback-classic:1.2.3"

    testCompile "io.micronaut:micronaut-http-client:$micronautVersion"
    testCompile "io.micronaut.test:micronaut-test-junit5:1.0.0.RC1"
    testRuntime "org.junit.jupiter:junit-jupiter-engine:5.1.0"
    runtime "ch.qos.logback:logback-classic:1.2.3"
}

```

### Example

```kotlin
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.NettyApplicationEngine
import io.micronaut.ktor.KotrApplication
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
