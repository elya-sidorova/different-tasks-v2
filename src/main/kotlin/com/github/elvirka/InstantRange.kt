package com.github.elvirka

import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant

class InstantRange(
    override val start: Instant,
    override val endInclusive: Instant
    ): ClosedRange<Instant> {
        init {
            require(start < endInclusive) { "Start must be less than end." }
        }
}

fun main() {
    IntRange
    val instant = "2022-06-01T22:19:15.000Z".toInstant()
    val start = "2022-06-01T22:19:01.000Z".toInstant()
    val end = "2022-06-01T22:19:44.000Z".toInstant()
    val range = InstantRange(end, start)
    println(instant in range)
    println(start < instant)
    println(end > instant)
    println(instant in start..end)
}