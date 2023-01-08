package com.github.elvirka.idiomaticKotlin

import java.io.File

const val filename = "src/main/kotlin/com/github/elvirka/idiomaticKotlin/day6input.txt"
val nl: String = System.lineSeparator()

fun main() {
    val groups = File(filename).readText().trim().split("$nl$nl")
    val totalAny = groups.sumOf { it.replace(nl, "").toSet().count() }
    println(totalAny)

    val total1 = groups.map {
        it.split(nl).map(String::toSet)
    }.sumOf {
        it.reduce { a, b -> a intersect b }.count()
    }
    println(total1)

    val total2 = groups
        .map { it.split(nl) }
        .sumOf { group ->
            group.map { it.toSet() }
                .reduce { a, b -> a intersect b }
                .count()
    }
    println(total2)

    var total = 0
    for (group in groups) {
        val answers: List<String> = group.split(nl)
        val answerSets: List<Set<Char>> = answers.map { it.toSet() }
        val intersection: Set<Char> = answerSets.reduce { a, b -> a intersect b }
        total += intersection.size
    }
}


