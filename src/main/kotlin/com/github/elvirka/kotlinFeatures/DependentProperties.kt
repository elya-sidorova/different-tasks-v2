package com.github.elvirka.kotlinFeatures

private data class Battery(var one: Double) {
    val two: Double
        get() = one * 5
}

fun main() {
    val battery = Battery(1.1)
    println(battery.one)
    println(battery.two)
    battery.one = 2.2
    println(battery.one)
    println(battery.two)
}