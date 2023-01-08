package com.github.elvirka.kotlinFeatures

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

private fun longOperation(): String {
    repeat(2_000_000) {
        print('.')
    }
    println()
    return "Done"
}

@OptIn(ExperimentalTime::class)
fun main() {
    val (value , time) = measureTimedValue {
        longOperation()
    }
    println("$value, $time")
}