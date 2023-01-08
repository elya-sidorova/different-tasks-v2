package com.github.elvirka.kotlinFeatures

import kotlinx.datetime.DateTimePeriod

fun main() {
    val list = listOf("PT3.4D", "P1Y2M3DT4H5M6S", "PT5.5H", "P2.5Y4M", "P12DT13H")
    list.forEach {
        println("-----$it-----")
        try {
            val duration = DateTimePeriod.parse(it)
            println(duration)
        } catch (e: Exception) {
            println(e.message)
        }
    }
}
