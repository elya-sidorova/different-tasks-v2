package com.github.elvirka

fun main() {
    val qwe = Qwe()
    qwe.print()
    val arrayOfString2D = arrayOf(
        arrayOf("Practice", "makes", "perfect"),
        arrayOf(1, 2)
    )
    println(arrayOfString2D.contentDeepToString())

    println("\u001B[101m   \u001B[0m")

    val list = listOf(1, 2, 3, 4).zipWithNext()
    println(list)

    println(listOf<Int>().tail())

    val statusPredicate: (Int) -> Boolean = { code -> code in 200 until 300 }
    println(statusPredicate(300))
    println(fib(4))

    val add: (Int, Int) -> Int = { x, y -> x + y }
    val curriedAdd: (Int) -> (Int) -> Int = { x -> { y -> x + y }}
    add(3, 5)
    val add3 = curriedAdd(3)
    add3(5)

}

fun <T> List<T>.tail(): List<T> =
    if (isEmpty()) {
        emptyList()
    }
    else subList(1, count())


fun fib(n: Int): Int =
    when (n) {
        0 -> 1
        1 -> 1
        else -> fib(n - 2) + fib(n - 1)
    }



