package com.github.elvirka

import kotlin.properties.Delegates.observable

fun main() {
    var names by observable(listOf<String>()) {
        _, old, new ->
            println("Names changed from $old to $new")
    }
    names += "Cat"
    names += "Dog"
}


