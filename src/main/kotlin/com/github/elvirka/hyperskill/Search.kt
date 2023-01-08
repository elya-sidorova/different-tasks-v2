package com.github.elvirka.hyperskill

import java.io.File

private val SpaceRegex = "\\s+".toRegex()

private enum class MenuItem(val code: Int) {
    FIND_A_PERSON(1),
    PRINT_ALL_PEOPLE(2),
    EXIT(0);

    companion object {
        private val mapping = values().associateBy { it.code }

        fun valueFromCode(code: Int): MenuItem? = mapping[code]
    }
}

private enum class Strategy {
    ALL, ANY, NONE;

    companion object {
        private val mapping = Strategy.values().associateBy { it.name }

        fun valueFromString(name: String): Strategy? = mapping[name.uppercase()]
    }
}

fun main(args: Array<String>) {
    val fileName = getFileNameFromArguments(args)
    if (fileName == null) {
        println("File path was not passed.")
        return
    }
    val file = File(fileName)
    if (!file.exists()) {
        println("File does not exist.")
        return
    }
    val dataSet = file.readLines()
    val invertedIndex = buildInvertedIndex(dataSet)
    while (true) {
        printMenu()
        when (MenuItem.valueFromCode(readln().toInt())) {
            MenuItem.FIND_A_PERSON -> findPeople(invertedIndex, dataSet)
            MenuItem.PRINT_ALL_PEOPLE -> printDataSet(dataSet)
            MenuItem.EXIT -> return
            null -> println("Incorrect option! Try again.")
        }
    }
}

private fun buildInvertedIndex(dataSet: List<String>): Map<String, Set<Int>> =
    dataSet.flatMapIndexed { index, s ->
        s.split(SpaceRegex).map { it.lowercase() to index }
    }.groupBy(
        keySelector = { it.first },
        valueTransform = { it.second }
    ).mapValues { it.value.toSet() }

private fun getFileNameFromArguments(args: Array<String>): String? =
    if (args.size == 2 && args[0] == "--data") args[1] else null

private fun findPeople(invertedIndex: Map<String, Set<Int>>, dataSet: List<String>) {
    println("Select a matching strategy: ALL, ANY, NONE:")
    val strategy = Strategy.valueFromString(readln())
    println("Enter a name or email to search all matching people:")
    val query = readln().lowercase().split(SpaceRegex)
    val indices = when(strategy) {
        Strategy.ALL -> findAll(invertedIndex, query)
        Strategy.ANY -> findAny(invertedIndex, query)
        Strategy.NONE -> findNone(invertedIndex, query)
        null -> emptyList()
    }

    val result = indices.map { dataSet[it] }
    result.forEach {
        println(it)
    }
    result.ifEmpty {
        println("No matching people found.")
    }
}

private fun findAll(invertedIndex: Map<String, Set<Int>>, query: List<String>): Set<Int> =
    query.mapNotNull { invertedIndex[it] }
        .fold(emptySet()) { a, b -> a.intersect(b) }

private fun findAny(invertedIndex: Map<String, Set<Int>>, query: List<String>): Set<Int> =
    query.mapNotNull { invertedIndex[it] }
        .fold(emptySet()) { a, b -> a.union(b) }

private fun findNone(invertedIndex: Map<String, Set<Int>>, query: List<String>): Set<Int> {
    val indicesToExclude = invertedIndex.filter { it.key in query }
        .flatMap { it.value }.toSet()
    return invertedIndex.flatMap { it.value }.subtract(indicesToExclude)
}


private fun printDataSet(dataSet: List<String>) {
    dataSet.forEach { println(it) }
}

private fun printMenu() {
    println("""
        === Menu === 
        1. Find a person
        2. Print all people
        0. Exit
    """.trimIndent()
    )
}
