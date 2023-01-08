package com.github.elvirka.adventOfCode

val map = mapOf(
    "X" to 1,
    "Y" to 2,
    "Z" to 3,
    "A" to 1,
    "B" to 2,
    "C" to 3
)

fun main() {
    val input = readResource("/adventofcode/input_2.txt")
    val result = input.split("\n")
        .map {
            it.split(" ")
        }.map { (p1, p2) ->
            when (p2) {
                "Y" -> p1 to p1
                "Z" -> p1 to when (p1) {
                    "A" -> "Y"
                    "B" -> "Z"
                    "C" -> "X"
                    else -> error("Wrong operand.")
                }
                "X" -> p1 to when (p1) {
                    "A" -> "Z"
                    "B" -> "X"
                    "C" -> "Y"
                    else -> error("Wrong operand.")
                }
                else -> error("Wrong operand.")
            }
        }
        .sumOf { (p1, p2) ->
            val points = when {
                map[p1]!! == map[p2]!! -> 3
                (p1 == "A" && p2 == "Y")
                    || (p1 == "B" && p2 == "Z")
                    || (p1 == "C" && p2 == "X") -> 6
                else -> 0
            } + map[p2]!!
            points
        }
    println(result)
}