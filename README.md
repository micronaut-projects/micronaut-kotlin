# Micronaut Kotlin

[![Maven Central](https://img.shields.io/maven-central/v/io.micronaut.kotlin/micronaut-kotlin-runtime.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22io.micronaut.kotlin%22%20AND%20a:%22micronaut-kotlin-runtime%22)
![Java CI](https://github.com/micronaut-projects/micronaut-kotlin/workflows/Java%20CI/badge.svg?branch=master)

This project includes integrations between [Micronaut](http://micronaut.io) and [Kotlin](https://kotlinlang.org/) and [Ktor](https://ktor.io/).

## Quick Start

To get started quickly with Micronaut + Kotlin you can use [Micronaut Launch](https://micronaut.io/launch/) either via the web browser or `curl` to create a correctly configured application with a Gradle build:

```bash
$ curl https://launch.micronaut.io/demo.zip?lang=kotlin -o demo.zip
$ unzip demo.zip -d demo
$ cd demo
$ ./gradlew run -t --watch-fs
```

## Documentation

See the [Documentation](https://micronaut-projects.github.io/micronaut-kotlin/latest/guide) for more information.

See the [Snapshot Documentation](https://micronaut-projects.github.io/micronaut-kotlin/snapshot/guide) for the current development docs.

## Snapshots and Releases

Snaphots are automatically published to [JFrog OSS](https://oss.jfrog.org/artifactory/oss-snapshot-local/) using [Github Actions](https://github.com/micronaut-projects/micronaut-kotlin/actions).

See the documentation in the [Micronaut Docs](https://docs.micronaut.io/latest/guide/index.html#usingsnapshots) for how to configure your build to use snapshots.

Releases are published to JCenter and Maven Central via [Github Actions](https://github.com/micronaut-projects/micronaut-kotlin/actions).

A release is performed with the following steps:

- [Publish the draft release](https://github.com/micronaut-projects/micronaut-kotlin/releases). There should be already a draft release created, edit and publish it. The Git Tag should start with `v`. For example `v1.0.0`.
- [Monitor the Workflow](https://github.com/micronaut-projects/micronaut-kotlin/actions?query=workflow%3ARelease) to check it passed successfully.
- Celebrate!
