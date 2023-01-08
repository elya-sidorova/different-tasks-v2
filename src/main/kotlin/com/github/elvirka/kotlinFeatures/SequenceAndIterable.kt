package com.github.elvirka

fun main() {
    sequenceOf(1,2,3)
        .filter { print("F$it, "); it % 2 == 1 }
        .map { print("M$it, "); it * 2 }
        .forEach { print("E$it, ") }
    println()

    listOf(1,2,3)
        .filter { print("F$it, "); it % 2 == 1 }
        .map { print("M$it, "); it * 2 }
        .forEach { print("E$it, ") }
    println()

    (1..10).asSequence()
        .filter { print("F$it, "); it % 2 == 1 }
        .map { print("M$it, "); it * 2 }
        .find { it > 5 }
    println()

    (1..10)
        .filter { print("F$it, "); it % 2 == 1 }
        .map { print("M$it, "); it * 2 }
        .find { it > 5 }
    println()

    generateSequence(1) { it + 1 }
        .map { it * 2 }
        .take(10)
        .forEach { print("$it, ") }
    println()

    val fibonacci = sequence {
        yield(1)
        var current = 1
        var prev = 1
        while (true) {
            yield(current)
            val temp = prev
            prev = current
            current += temp
        }
    }
    print(fibonacci.take(10).toList())
    println()
}