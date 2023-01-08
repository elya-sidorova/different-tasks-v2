package com.github.elvirka.patternsBook

import java.util.Collections

object NoMoviesList: List<String> {
    override val size: Int
        get() = 0

    override fun contains(element: String): Boolean = false

    override fun containsAll(elements: Collection<String>): Boolean = false

    override fun get(index: Int): String = throw IndexOutOfBoundsException()

    override fun indexOf(element: String): Int = -1

    override fun isEmpty(): Boolean = true

    override fun iterator(): Iterator<String> = Collections.emptyIterator()

    override fun lastIndexOf(element: String): Int = -1

    override fun listIterator(): ListIterator<String> = Collections.emptyListIterator()

    override fun listIterator(index: Int): ListIterator<String> = Collections.emptyListIterator()

    override fun subList(fromIndex: Int, toIndex: Int): List<String> = emptyList()
}

fun printMovies(movies: List<String>) {
    for (m in movies) { println(m) }
}

fun main() {
    val myList = NoMoviesList
    val myList1 = NoMoviesList
    println(myList == myList1)
    printMovies(myList)
}