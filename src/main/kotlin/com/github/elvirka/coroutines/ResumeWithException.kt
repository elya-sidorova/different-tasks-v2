package com.github.elvirka.coroutines

import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

suspend fun main() {
    try {
        suspendCoroutine<Unit> {
            it.resumeWithException(IllegalArgumentException("Just an exception"))
        }
    } catch (e: IllegalArgumentException) {
        println("Caught: ${e.message}")
    }
}