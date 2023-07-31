plugins {
    id("io.micronaut.application")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
}

version = "0.1"
group = "example.micronaut"

repositories {
    mavenCentral()
}

micronaut {
    importMicronautPlatform = false
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("example.micronaut.*")
    }
}

configurations.all {
    resolutionStrategy {
        preferProjectModules()
    }
}

dependencies {
    implementation(projects.micronautKotlinRuntime)
    implementation(mnSerde.micronaut.serde.jackson)
    implementation(mnReactor.micronaut.reactor)
    implementation(mn.micronaut.http.client)
    runtimeOnly(mnLogging.logback.classic)
    testImplementation(mnTest.micronaut.test.junit5)
    //runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

application {
    mainClass.set("example.micronaut.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}
