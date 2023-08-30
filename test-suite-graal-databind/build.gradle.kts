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
    implementation(mn.micronaut.http.client)
    implementation(mn.micronaut.jackson.databind)
    implementation(libs.kotlin.reflect)
    implementation(mnReactor.micronaut.reactor)
    runtimeOnly(mnLogging.logback.classic)
    testImplementation(mnTest.micronaut.test.junit5)
}

application {
    mainClass.set("example.micronaut.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}