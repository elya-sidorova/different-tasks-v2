package com.github.elvirka.libraries.arrow

import arrow.core.compose
import arrow.core.partially1

private val squaredPlusOne: (Int) -> Int =
    { x: Int -> x * 2 } compose { it + 1 }

private data class Person(val name: String, val ege: Int)

fun main() {
    println( squaredPlusOne(3) )

    val people = listOf(
        Person("Nic", 25, ),
        Person("Vic", 32)
    )

    val res = people.filter(
        Boolean::not compose ::longerThen3 compose Person::name
    )
    println(res)

    val res1 = people.filter { !longerThen3(it.name) }
    println(res1)

    val res2 = people.filter(
        Boolean::not
            compose (String::isPrefixOf).partially1("V")
            compose Person::name
    )
    println(res2)

}

private fun longerThen3(str: String): Boolean {
    return str.length > 3
}

private fun String.isPrefixOf(s: String) = s.startsWith(this)

