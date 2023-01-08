package com.github.elvirka.hyperskill

import java.io.File

fun main() {
    val dir = File("/Users/elvirasidorova/Downloads/basedir")

    val emptyDirs = dir.walk(FileWalkDirection.TOP_DOWN).filter {
        it.list()?.size == 0
    }.map { it.name }
    println(emptyDirs.toSet().joinToString(" "))
    File("/Users/elvirasidorova/qwe.txt").writeText(
        emptyDirs.toSet().joinToString(" ")
    )
}

