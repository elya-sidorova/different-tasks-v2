package com.github.elvirka

import java.time.Duration
import java.time.LocalTime

private val MINIMAL_GAP_DURATION: Duration = Duration.ofMillis(100)

private fun isGap(from: LocalTime, to: LocalTime) = Duration.between(from, to) > MINIMAL_GAP_DURATION

fun main() {
    val timeList1 = listOf(
        LocalTime.of(0, 0,  35)..LocalTime.of(0, 0,  44),
        LocalTime.of(0, 0,  51)..LocalTime.of(0, 1,  13),
        LocalTime.of(0, 1,  22)..LocalTime.of(0, 1,  40),
    )
    val timeList2 = listOf(
        LocalTime.of(0, 0,  35)..LocalTime.of(0, 0,  51),
        LocalTime.of(0, 0,  51)..LocalTime.of(0, 1,  13),
    )

    val intervalLists = listOf(timeList1, timeList2)

    val startTime = intervalLists.map { it.first() }.minOf { it.start }
    val endTime = intervalLists.map { it.last() }.minOf { it.endInclusive }

    var overlaps = mutableListOf(startTime..endTime)
    var newOverlaps = mutableListOf<ClosedRange<LocalTime>>()
    for (intervals in intervalLists) {
        intervals@ for (range in intervals) {
            overlaps@ for (overlapRange in overlaps) {
                if (range.endInclusive < overlapRange.start) {
                    continue@intervals
                } else if (range.start > overlapRange.endInclusive) {
                    continue@overlaps
                } else {
                    val from = if (range.start > overlapRange.start) {
                        range.start
                    } else {
                        overlapRange.start
                    }
                    val to = if (range.endInclusive < overlapRange.endInclusive) {
                        range.endInclusive
                    } else {
                        overlapRange.endInclusive
                    }
                    if (isGap(from, to)) {
                        newOverlaps.add(from..to)
                    }
                    continue@intervals
                }
            }
        }
        overlaps = newOverlaps
        newOverlaps = mutableListOf()
    }
    println(overlaps)
}