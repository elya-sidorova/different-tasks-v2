package com.github.elvirka.kotlinFeatures

private data class GenericContainer<T> (val values: List<T>)

private fun printValues(
    containers: List<GenericContainer<*>>,
){
    containers.map { container ->
        container.values.map {
            println(it)
        }
    }
}

fun main() {
    val container = GenericContainer(
        values = listOf(1, "adventofcode")
    )
    printValues(listOf(container))
}