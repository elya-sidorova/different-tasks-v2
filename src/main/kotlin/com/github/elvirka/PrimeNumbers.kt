package com.github.elvirka

import kotlin.system.measureTimeMillis

private fun isPrime(dividend: Int, divisor: Int): Boolean =
    when {
        dividend < 2 -> false
        divisor == 1 -> true
        dividend % divisor == 0 -> false
        else -> isPrime(dividend, divisor - 1)
    }

private fun isPrime2(n: Int, i: Int = 2): Boolean =
    when {
        n <= 2 -> n == 2
        n % i == 0 -> false
        i * i > n -> true
        else -> isPrime2(n, i + 1)
    }


fun main() {
    while (true) {

        val n = readln().toInt()
        println(
            measureTimeMillis { isPrime(n, n - 1).also(::println) }
        )
        println(
            measureTimeMillis { isPrime2(n).also(::println) }
        )
    }
}



