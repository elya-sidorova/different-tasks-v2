package com.github.elvirka.adventOfCode

fun main() {
    val input = readResource("/adventofcode/input_1.txt")
    val inventories = input.split("\n\n")
        .map { inventory ->
            inventory.split("\n").sumOf { it.toInt() }
        }
    var first = inventories[0]
    var second = inventories[0]
    var third = inventories[0]
    inventories.map {  sum ->
        when {
            sum > first -> {
                third = second
                second = first
                first = sum
            }
            sum > second -> {
                third = second
                second = sum
            }
            sum > third -> {
                third = sum
            }
        }
    }
    println(first + second + third)
}