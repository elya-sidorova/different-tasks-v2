package com.github.elvirka.adventOfCode

class Util

fun readResource(path: String): String =
    Util::class.java.getResource(
        path
    )!!.readText().trimEnd()