package com.github.elvirka.coroutines

import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    println(fibonacci.take(15).toList())
}

private val fibonacci = sequence {
    var first = 0L
    yield(first)
    var second = 1L
    yield(second)
    while (true) {
        val tmp = first
        first = second
        second += tmp
        yield(second)
    }
}