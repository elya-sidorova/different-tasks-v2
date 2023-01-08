package com.github.elvirka.adventOfCode

fun main() {
    val input = readResource("/adventofcode/input_3.txt")
    val sum = input.lines().chunked(3)
        .flatMap { (p1, p2, p3) ->
            p1.toSet().intersect(p2.toSet()).intersect(p3.toSet())
        }
        .sumOf {
            if (it.isLowerCase()) {
                it.code - 'a'.code + 1
            } else {
                it.code - 'A'.code + 1 + 26
            }
        }
    println(sum)
}