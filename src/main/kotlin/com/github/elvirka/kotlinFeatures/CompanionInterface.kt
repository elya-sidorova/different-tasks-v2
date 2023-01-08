package com.github.elvirka.kotlinFeatures

interface DefaultEnum<E : Enum<E>> {
    val default: E
}

interface Parsable<V> {
    fun parse(input: String): V?
}

enum class Enum1{
    X1, X2, X3, X4;
    companion object : DefaultEnum<Enum1>, Parsable<Enum1> {
        override val default: Enum1 = X2
        override fun parse(input: String): Enum1? =
            parseEnum<Enum1>(input)
    }
}

fun genericPrint(e: DefaultEnum<*>): Unit = println(e.default.name)

fun <V> genericParse(e: Parsable<V>): V? = e.parse(readln())

inline fun <reified V: Enum<V>> parseEnum(input: String): V? =
    enumValues<V>().firstOrNull { it.name == input }

fun main() {
    genericPrint(Enum1)
    val value = genericParse(Enum1)
    println(value)
}
