package com.github.elvirka

fun main() {
    val list1 = listOf(0..2, 3..5, 8..10)
    val list2 = listOf(0..2, 3..5, 8..10)

    val lists = listOf(list1, list2)
    val sweepingLine = MutableList(11) { 0 }
    for (list in lists) {
        for (range in list) {
            for (i in range) {
                sweepingLine[i] = sweepingLine[i] + 1
            }
        }
    }
    println(sweepingLine)
    val gaps = mutableListOf<IntRange>()
    var from = -1
    for ((index, value) in sweepingLine.withIndex()) {
        if (value == 0 && from == -1) {
            from = index
        }
        if (value == 2 && from != -1) {
            gaps.add(from until index)
            from = -1
        }
    }
    println(gaps)
}