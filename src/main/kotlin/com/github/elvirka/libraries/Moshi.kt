package com.github.elvirka.libraries

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

private class Human(var name: String, var age: Int, var friends: Array<String>)

fun main() {
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val human = Human("Mike", 20, arrayOf("Alex", "Valery", "Ann"))
    val humanAdapter = moshi.adapter(Human::class.java)
    println(humanAdapter.toJson(human))

    val json = """{"name":"Mike","age":20,"friends":["Alex","Valery","Ann"]}"""
    val newHuman = humanAdapter.fromJson(json)
    println(newHuman)

    val type = Types.newParameterizedType(List::class.java, Human::class.java)
    val humanList = listOf(human, newHuman)
    val humanListAdapter = moshi.adapter<List<Human?>>(type)
    println(humanListAdapter.toJson(humanList))

    println(human.friends.contentToString())
}