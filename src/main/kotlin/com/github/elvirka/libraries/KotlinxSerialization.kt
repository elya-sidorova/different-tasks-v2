package com.github.elvirka.libraries

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import kotlinx.serialization.json.putJsonObject

@Serializable
data class Data(val date: LocalDateTime)

fun main() {
    val str = """{"date": "2021-04-29T15:47:32.842+0300"}"""
    val obj: Data = Json.decodeFromString(str)
    println(obj)
    val element = buildJsonObject {
        put("name", "kotlinx.serialization")
        putJsonObject("owner") {
            put("name", "kotlin")
        }
        putJsonArray("forks") {
            addJsonObject {
                put("votes", 42)
            }
            addJsonObject {
                put("votes", 9000)
            }
        }
        put("something", getSomething(true))
        put("somethingMore", getSomething(false))
    }
    println(element)
}

fun getSomething(bool: Boolean): JsonElement =
    if (bool) {
        JsonPrimitive(5)
    } else {
        buildJsonObject{
            put("type", 5)
        }
    }