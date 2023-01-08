package com.github.elvirka.adventOfCode

fun main() {
    val input = readResource("/adventofcode/input_5.txt")
    val (crateStr, movesStr) = input.split("\n\n", limit = 2)
    val crateLines = crateStr.lines()
    val reversedCrates = crateLines
        .subList(fromIndex = 0, toIndex = crateLines.lastIndex)
        .reversed()
        .map { line ->
            line.chunked(4).map {
                if (it.isBlank()) {
                    null
                } else {
                    it.trim().removeSurrounding("[", "]")
                }
            }
        }
    val crates = List<MutableList<String>>(reversedCrates[0].size) {
        mutableListOf()
    }
    for (i in 0.. reversedCrates.lastIndex) {
        for (j in 0..reversedCrates[i].lastIndex) {
            reversedCrates[i][j]?.let { crates[j].add(it) }
        }
    }
    println(crates)

    val moves = movesStr.lines().mapNotNull { move ->
        movesRegex.find(move)?.groupValues?.subList(1,4)?.map { it.toInt() }
    }
    moves.map { (count, from, to) ->
        repeat(count) {
            crates[to - 1].add(crates[from - 1].last())
            crates[from - 1].removeAt(crates[from - 1].lastIndex)
        }
    }
    println(crates.joinToString("") { it.last() })
}

val movesRegex = """move (\d+) from (\d+) to (\d+)""".toRegex()

