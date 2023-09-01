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
    version(libs.versions.micronaut.platform.get())
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
    implementation(mnSerde.micronaut.serde.jackson)
    implementation(mnReactor.micronaut.reactor)

    // Added manually as graalvm-community-jdk-17.0.8_linux-x64_bin on CI does not get detected as Graal (missing java.vendor.version)
    implementation(mn.micronaut.graal)

    runtimeOnly(mnLogging.logback.classic)
    testImplementation(mnTest.micronaut.test.junit5)
}

application {
    mainClass.set("example.micronaut.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}
