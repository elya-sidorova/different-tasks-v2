package com.github.elvirka.kotlinFeatures

fun main() {
    val map = mapOf(CustomField.SUBTOPIC.name to "test")
    val fields = CustomFields(map)
    println(fields.SUBJECT)
}

class CustomFields(val map: Map<String, String?>) {
    private val defaultMap = map.withDefault { null }
    val SUBJECT: String? by defaultMap
}

enum class CustomField {
    SUBJECT, SUBTOPIC
}


