package com.github.elvirka

import java.util.UUID

fun main() {
    val email = "elvirrrka@gmail.com"
    val result = UUID.nameUUIDFromBytes(email.toByteArray()).toString()
    println(result)

    println(UUID.fromString("00000000-0000-0000-0000-000000000005"))
    println(UUID.nameUUIDFromBytes("1".toByteArray()))
}

