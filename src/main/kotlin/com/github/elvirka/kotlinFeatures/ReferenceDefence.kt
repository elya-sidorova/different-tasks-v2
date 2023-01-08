package com.github.elvirka.kotlinFeatures

fun main() {
    val userRepository = UserRepository()
    val storedUsers = userRepository.loadAll()
    //storedUsers[4] = "Bob"
    println(userRepository.loadAll())
}

private class UserRepository {
    private val storedUsers: MutableMap<Int, String> = mutableMapOf()

    fun loadAll(): Map<Int, String> {
        return storedUsers
    }
}

