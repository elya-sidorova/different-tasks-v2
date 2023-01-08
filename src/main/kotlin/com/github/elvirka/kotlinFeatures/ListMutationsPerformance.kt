package com.github.elvirka

import kotlin.system.measureNanoTime

fun main() {
    var list = listOf<Int>()
    val mlist = mutableListOf<Int>()

    measureNanoTime {
        for (i in 1..1000) {
            list += i
        }
    }.let { println(it) }
    measureNanoTime {
        for (i in 1..1000) {
            mlist += i
        }
    }.let { println(it) }
}


