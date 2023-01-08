package com.github.elvirka.coroutines

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield

fun main(): Unit = runBlocking {
    launch {
        processOne()
    }

    launch {
        processTwo()
    }
}

private suspend fun processOne() {
    for (i in 1..10) {
        println(1)
        if (i == 2) {
            //delay(100)
            yield()
        }
    }
}

private suspend fun processTwo() {
    for (i in 1..10) {
        println(2)
        if (i == 2) {
            //delay(100)
            yield()
        }
    }
}



/*inline fun <reified T> ApplicationConfig.property(key: String): T {
    val property = this.property(key)
    return when(T::class) {
        String::class -> property.getString() as T
        List::class -> property.getList() as T
        else -> throw IllegalArgumentException("Wrong property type")
    }
}*/

