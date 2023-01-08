package com.github.elvirka.patternsBook

fun main() {
}

val allUsers = mutableListOf<User>()

fun createUser(_name: String, role: Role) {
    for (u in allUsers) {
        if (u.role == role) {
            allUsers += u.copy(name = _name)
            return
        }
    }
}

enum class Role {
    ADMIN,
    SUPER_ADMIN,
    REGULAR_USER
}

data class User(
    val name: String,
    val role: Role,
    private val permissions: Set<String>,
    val tasks: List<String>,
) {
    fun hasPermission(permission: String): Boolean =
        permission in permissions
}