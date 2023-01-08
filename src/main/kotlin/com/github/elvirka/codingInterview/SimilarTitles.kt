package com.github.elvirka.codingInterview

val titles = listOf("duel", "dule", "speed", "spede", "deul", "cars" )

fun main() {
    val groups = titles.groupBy { it.toToken() }
    println(groups)
    println(groups["speed".toToken()])
}

private fun String.toToken(): String =
    this.fold( IntArray(26) {0} ) {
            acc, c -> acc[c - 'a'] += 1; acc
    }.joinToString("#")