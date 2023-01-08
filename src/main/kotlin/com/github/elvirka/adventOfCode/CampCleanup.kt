package com.github.elvirka.adventOfCode

fun main() {
    val input = readResource("/adventofcode/input_4.txt")
    val count = input.lines().map { line ->
        line.split(",", limit = 2).map { range ->
            val (b1, b2) = range.split("-")
            b1.toInt()..b2.toInt()
        }
    }.count { (r1, r2) ->
        r1.intersect(r2) != emptySet<Int>() ||
            r2.intersect(r1) != emptySet<Int>()
    }
    println(count)
}