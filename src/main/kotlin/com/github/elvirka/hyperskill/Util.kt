package com.github.elvirka.hyperskill

interface Parsable<V> {
    fun parse(input: String): V?
}

interface PrintableEnum {
    fun valuesToString(): String
}

inline fun <reified V : Enum<V>> enumFromString(
    input: String,
    fieldSelector: (V) -> String,
): V? =
    enumValues<V>().firstOrNull {
        fieldSelector(it).equals(input, ignoreCase = true)
    }

inline fun <reified V : Enum<V>> enumValuesToString(
    lowercase: Boolean = false,
    crossinline fieldSelector: (V) -> String,
): String =
    enumValues<V>()
        .joinToString(", ", prefix = "(", postfix = ")")
        { value ->
            fieldSelector(value).let {
                if (lowercase) it.lowercase() else it
            }
        }

class ConsoleReaderWithRetry<V>(
    private val parsableClass: Parsable<V>,
    private val promptMessage: String,
    private val errorMessage: String? = null,
    private val addInputToErrorMessage: Boolean = false,
    private val validator: (V) -> Boolean = { true },
) {
    fun read(): V {
        var value: V?
        do {
            println(promptMessage)
            value = parsableClass.parse(readln())
            if (value == null) {
                errorMessage?.also { println(errorMessage) }
            } else {
                val result = validator(value)
                if (!result) {
                    errorMessage?.also {
                        if (addInputToErrorMessage) {
                            println("$errorMessage $value")
                        } else {
                            println(errorMessage)
                        }
                    }
                    value = null
                }
            }
        } while (value == null)
        return value
    }
}