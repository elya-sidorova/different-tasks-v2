package com.github.elvirka.dsl

import java.time.LocalDate
import java.time.LocalDate.now
import java.time.Period

//operator fun LocalDate.plus(days: Long): LocalDate = plusDays(days)

private val Int.days: Period
    get() = Period.ofDays(this)

@Suppress("EnumEntryName")
private enum class TimeDirections {
    before, after;
}

private val before = TimeDirections.before
private val after = TimeDirections.after

private infix fun Int.days(direction: TimeDirections): Period =
    when(direction) {
        TimeDirections.before -> Period.ofDays(-this)
        TimeDirections.after -> Period.ofDays(this)
    }


fun main() {
    val dueDate = LocalDate.of(2020, 8, 16)
    //dueDate + 15
    println(dueDate + 15.days)

    println( now() + (2 days before) )
}