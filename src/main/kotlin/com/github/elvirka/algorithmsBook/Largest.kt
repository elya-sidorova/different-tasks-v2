package com.github.elvirka.algorithmsBook

import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    val list = (1..10).map { Random.nextInt(10) }
    //println(list)
    println(list.largest())
    println(list.largest2())
    println(list.sorting2())
    println(list.double2())
    println(list)
    println("-------------------")

    //val p = listOf(3, 1, 4, 1, 5, 9, 2, 6)
    val p = (1..2_097_152).map { Random.nextInt(1000) }
    //println(p)

    println(p.largest2())
    println(p.sorting2())
    println(p.double2())
    println(p.tournament2())
    measureTimeMillis { p.largest2() }.also { println(it) }
    measureTimeMillis { p.sorting2() }.also { println(it) }
    measureTimeMillis { p.double2() }.also { println(it) }
    measureTimeMillis { p.tournament2() }.also { println(it) }

}

fun List<Int>.largest2(): Pair<Int, Int> {
    var max = this[0]
    var secondMax = this[1]
    if (secondMax > max) {
        max = secondMax.also { secondMax = max }
    }
    for (v in this.subList(2, this.lastIndex + 1)) {
        if (v > max) {
            secondMax = max
            max = v
        } else if (v > secondMax) {
            secondMax = v
        }
    }
    return max to secondMax
}

fun List<Int>.sorting2(): Pair<Int, Int> {
    val (max, secondMax) = this.sortedDescending().slice(0..1)
    return max to secondMax
}

fun List<Int>.double2(): Pair<Int, Int> {
    val copy = this.toMutableList()
    val index = copy.indices.maxByOrNull { copy[it] } ?: error("Empty list.")
    val max = copy[index]
    copy.removeAt(index)
    val secondMax = copy.maxOf { it }
    return max to secondMax
}

fun List<Int>.tournament2(): Pair<Int, Int> {
    val n = this.size
    val winner = IntArray(n - 1){ 0 }
    val loser = IntArray(n - 1){ 0 }
    val prior = IntArray(n - 1){ -1 }

    var idx = 0
    for (i in 0 until n step 2) {
        if (this[i] < this[i + 1]) {
            winner[idx] = this[i + 1]
            loser[idx] = this[i]
        } else {
            winner[idx] = this[i]
            loser[idx] = this[i + 1]
        }
        idx += 1
    }

    var m = 0
    while (idx < n - 1) {
        if (winner[m] < winner[m + 1]) {
            winner[idx] = winner[m + 1]
            loser[idx]  = winner[m]
            prior[idx]  = m + 1
        } else {
            winner[idx] = winner[m]
            loser[idx]  = winner[m + 1]
            prior[idx]  = m
        }
        m += 2
        idx += 1
    }
    //println(winner.joinToString("\t"))
    //println(loser.joinToString("\t"))
    //println(prior.joinToString("\t"))

    val largest = winner[m]
    var second = loser[m]

    m = prior[m]

    while (m >= 0){
        if (second < loser[m]) {
            second = loser[m]
        }
        m = prior[m]
    }
    return largest to second
}


fun List<Int>.largest(): Int {
    var max = this[0]
    for (v in this.subList(1, this.lastIndex + 1)) {
        if (v > max) {
            max = v
        }
    }
    return max
}
