package com.github.elvirka.libraries

import com.github.avrokotlin.avro4k.Avro
import com.github.avrokotlin.avro4k.io.AvroDecodeFormat
import com.github.avrokotlin.avro4k.io.AvroEncodeFormat
import kotlinx.serialization.Serializable
import java.io.ByteArrayOutputStream
import java.io.File

@Serializable
data class Ingredient(val name: String, val sugar: Double, val fat: Double)

@Serializable
data class Name(val name: String)

@Serializable
data class Pizza(val name: Name, val ingredients: List<Ingredient>, val vegetarian: Boolean, val kcals: Int)

fun main() {
    val veg = Pizza(Name("veg"), listOf(Ingredient("peppers", 0.1, 0.3), Ingredient("onion", 1.0, 0.4)), true, 265)

    val schema = Avro.default.schema(Pizza.serializer())
    //println(schema.toString(true))

    val baos = ByteArrayOutputStream()
    Avro.default.openOutputStream(Pizza.serializer()) {
        encodeFormat = AvroEncodeFormat.Json
    }.to(baos).write(veg).close()
    val jsonString = String(baos.toByteArray())
    println(jsonString)

    val output = Avro.default.openOutputStream(Pizza.serializer()) {
        encodeFormat = AvroEncodeFormat.Json
        this.schema = schema
    }.to(File("pizzas.avro"))
    output.write(listOf(veg))
    output.close()

    val input = Avro.default.openInputStream(Pizza.serializer()) {
        decodeFormat = AvroDecodeFormat.Data(schema)
    }.from(File("pizzas.avro"))
    input.iterator().forEach { println(it) }
    input.close()

    println("!!!!!!")
    val record = Avro.default.toRecord(Pizza.serializer(), veg)
    println(record)
    val name = record.get("name")
    println(name)
    val obj = Avro.default.fromRecord(Pizza.serializer(), record)
    println(obj)
}
