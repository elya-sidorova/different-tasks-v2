package com.github.elvirka

private val seq = sequence {
    yield(1)
    yield(2)
    yield(3)
}

fun main() {
    for (num in seq) {
        println(num)
    }

    val fruits = listOf("🍏", "🍒", "🍎", "🍊", "🍓", "🥝", "🍌")
    fruitsSequence(fruits)
        .forEach { println(it) }
}

private fun fruitsSequence(fruits: List<String>) =
    fruits.asSequence()
        .map { println(it); it  }