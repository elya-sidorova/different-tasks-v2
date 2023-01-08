package com.github.elvirka.codingInterview

fun main() {
    val res = f(4)
    println(res)
}

fun f(n: Int): Int =
    if (n <= 0) {
        1
    } else {
        f(n - 1) + f(n - 1)
    }