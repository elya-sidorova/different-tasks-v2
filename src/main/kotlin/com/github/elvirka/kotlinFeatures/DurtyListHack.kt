package com.github.elvirka

fun main() {
    val list = listOf(1, 2, 3)
    val list1 = list.myMap { it * 2 }
    if (list1 is ArrayList) {
        list1.add(4)
    }
    println(list1)
}

inline fun <T, R> Iterable<T>.myMap(
    transformation: (T) -> R
) : List<R> {
    val list = ArrayList<R>()
    for (elem in this){
        list.add(transformation(elem))
    }
    return list
}