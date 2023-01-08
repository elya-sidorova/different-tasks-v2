package com.github.elvirka

import java.time.LocalDate
import java.time.LocalDateTime

fun main() {
    val now = LocalDateTime.now()
    val start = LocalDate.parse("2021-10-17").atStartOfDay()
    val end = LocalDate.parse("2021-10-21").atStartOfDay()
    if(now > start) { /*...*/ }
    if(now < end) { /*...*/ }
    if(now in start..end) { /*...*/ }

    if(end isBefore start) { /*...*/ }
}

infix fun LocalDateTime.isBefore(other: LocalDateTime) =
    this.isBefore(other)