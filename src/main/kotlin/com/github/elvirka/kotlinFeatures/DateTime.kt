package com.github.elvirka.kotlinFeatures

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlin.time.Duration

fun main() {
    val instant1 = Instant.parse("2000-01-01T20:00:00Z")
    val instant2 = Instant.parse("2012-10-14T05:20:30Z")

    val duration: Duration = instant2 - instant1
    println(duration)
    println(duration.inWholeDays)
    println(duration.inWholeHours)

    val d = Duration.parse("P4DT4H5M6S")
    println(d)

    val instant3 = Instant.parse("2000-01-01T20:00:00Z")
    val instant4 = Instant.parse("2000-10-14T00:00:00Z")

    val period: DateTimePeriod = instant3.periodUntil(instant4, TimeZone.UTC)
    println(period)

    val estime = Instant.fromEpochMilliseconds(1671688620308)
    println(estime.toString())
    val estime1 = Instant.fromEpochMilliseconds(1671695011975)
    println(estime1.toString())
    println(estime - estime1)
}
