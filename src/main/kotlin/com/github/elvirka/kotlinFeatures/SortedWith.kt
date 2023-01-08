package com.github.elvirka

fun main() {
    val fruits = listOf(
        "Blueberry", "Banana", "Orange", "Apple", "Strawberry", "Cherry"
    )
    fruits.sortedWith(
        compareBy<String> { it.length }.thenBy { it }
    ).let { println(it) }
}