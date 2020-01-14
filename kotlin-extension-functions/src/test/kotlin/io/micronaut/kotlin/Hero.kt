package io.micronaut.kotlin

import java.time.LocalDateTime

data class Hero(
        val id: Long,
        val name: String,
        val alterEgo: String,
        val dateCreated: LocalDateTime
) {
    fun equals(other: Hero): Boolean {
        return this.id == other.id && this.name == other.name && this.alterEgo == other.alterEgo
    }
}