package com.github.elvirka.libraries

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

@JvmInline
private value class Fruit(val value: String)

private data class Container(val values: List<Fruit>)

private val mapper: ObjectMapper = jacksonObjectMapper()

fun main() {
    val fruit = mapper.writeValueAsString(Container(listOf(Fruit("🍒"))))
    println(fruit)
    val json = """{"fruits":["🍒"]}"""
    val fruitObject = mapper.readValue<Container>(json)
    println(fruitObject)
}