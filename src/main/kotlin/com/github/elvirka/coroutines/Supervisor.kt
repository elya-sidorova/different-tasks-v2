package com.github.elvirka.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope

fun main(): Unit = runBlocking {
    launch {
        supervisorScope {
            launch {
                kotlin.runCatching { launch { throwSomething() } }
                    .fold(
                        onSuccess = { println("No error") },
                        onFailure = { it.printStackTrace() }
                    )
            }
            launch {
                kotlin.runCatching { doSomething() }
                    .fold(
                        onSuccess = { println("No error") },
                        onFailure = { it.printStackTrace() }
                    )
            }
        }
    }

}

suspend fun doSomething() {
    delay(500)
    println("ok")
}

suspend fun throwSomething() {
    delay(100)
    repeat(3) {
        throw IllegalArgumentException("Error $it")
    }

}