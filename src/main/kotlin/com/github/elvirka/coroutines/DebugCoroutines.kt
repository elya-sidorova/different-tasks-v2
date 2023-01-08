package com.github.elvirka.coroutines

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() = runBlocking(CoroutineName("parent")) {
    val a = async(CoroutineName("a")) {
        println("I'm computing part of the answer")
        6
    }
    val b = async(CoroutineName("b")) {
        println("I'm computing another part of the answer")
        7
    }
    println("The answer is ${a.await() * b.await()}")
}