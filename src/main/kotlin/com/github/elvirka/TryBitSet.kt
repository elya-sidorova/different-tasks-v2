package com.github.elvirka

import java.util.BitSet

fun main() {
    val n = 1
    val bs: BitSet = BitSet.valueOf(longArrayOf(n.toLong()))
    println(bs.get(0))
    println(bs.get(1))
    println(bs.toString())
}