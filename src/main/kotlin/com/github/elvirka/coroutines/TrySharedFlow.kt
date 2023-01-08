package com.github.elvirka.coroutines

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

suspend fun main() = coroutineScope{
    val sharedFlow = MutableSharedFlow<Char>(0)
    sharedFlow.emit('X')

    launch {
        for (c in 'A'..'E') {
            delay(300)
            sharedFlow.emit(c)
        }
    }
    sharedFlow.onEach {
        delay(1000)
        println(it)
    }.collect()
}