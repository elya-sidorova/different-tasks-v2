package com.github.elvirka

import java.time.LocalTime
import java.time.temporal.ChronoUnit


fun main() {
    val timeList1 = listOf(
        LocalTime.of(0, 0,  35)..LocalTime.of(0, 0,  51),
        LocalTime.of(0, 0,  51)..LocalTime.of(0, 1,  13),
        LocalTime.of(0, 1,  22)..LocalTime.of(0, 1,  40),
    )
    val timeList2 = listOf(
        LocalTime.of(0, 0,  35)..LocalTime.of(0, 0,  51),
        LocalTime.of(0, 0,  51)..LocalTime.of(0, 1,  13),
        LocalTime.of(0, 1,  22)..LocalTime.of(0, 1,  40),
    )
    val lists = listOf(timeList1, timeList2)
    val gaps = findGaps(lists)
    println(gaps)
}

private fun findGaps(lists: List<List<ClosedRange<LocalTime>>>): List<ClosedRange<LocalTime>> {
    val startTime = lists.map { it.first() }.minOf { it.start }
    val endTime = lists.map { it.last() }.minOf { it.endInclusive }
    val totalDuration = ChronoUnit.SECONDS.between(startTime, endTime).toInt()
    val sweepLine = MutableList(totalDuration + 1) { 0 }
    for (list in lists) {
        for (range in list) {
            val fromStartDuration = ChronoUnit.SECONDS.between(startTime, range.start).toInt()
            val rangeDuration = ChronoUnit.SECONDS.between(range.start, range.endInclusive).toInt()
            for (i in 0..rangeDuration) {
                sweepLine[fromStartDuration + i] = sweepLine[fromStartDuration + i] + 1
            }
        }
    }
    println(sweepLine)
    val gaps = mutableListOf<ClosedRange<LocalTime>>()
    var from = -1
    for ((index, value) in sweepLine.withIndex()) {
        if (value == 0 && from == -1) {
            from = index
        }
        if (value == 2 && from != -1) {
            gaps.add(startTime.plusSeconds(from.toLong() - 1)..startTime.plusSeconds(index.toLong()))
            from = -1
        }
    }
    return gaps
}