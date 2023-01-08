package com.github.elvirka.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope{
    val stateFlow = MutableStateFlow('X')

    launch {
        for (c in 'A'..'E') {
            delay(300)
            stateFlow.emit(c)
        }
    }
    stateFlow.onEach {
        delay(1000)
        println(it)
    }.collect()
}