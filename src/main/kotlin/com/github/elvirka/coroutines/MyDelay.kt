package com.github.elvirka.coroutines

import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private val executor = Executors.newSingleThreadScheduledExecutor {
    Thread(it, "scheduler").apply { isDaemon = true }
}

private suspend fun myDelay(time: Long) {
    suspendCoroutine<Unit> {
        executor.schedule({ it.resume(Unit) }, time, TimeUnit.MILLISECONDS)
    }
}

suspend fun main() {
    println("Before")
    myDelay(1000)
    println("After")
}