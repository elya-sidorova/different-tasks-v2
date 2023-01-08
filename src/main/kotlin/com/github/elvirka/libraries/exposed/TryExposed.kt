package com.github.elvirka.libraries.exposed

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object Teams : UUIDTable() {
    val name = varchar("name", 100)
}

class Team(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Team>(Teams)
    var name by Teams.name
}

fun main() {
    Database.connect("jdbc:postgresql://192.168.1.45:5432/elvirium", user = "elya", password = "PusecRad")

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create (Teams)

        val uid = UUID.randomUUID()
        println(uid)

        val team = Teams.insert {
            it[id] = uid
            it[name] = "User Care"
        } get Teams.id
        println(team.value)

        val otherTeam = Teams.insert {
            it[name] = "R&D"
        } get Teams.id
        println(otherTeam.value)

        val duplicateTeam = Teams.insertIgnore {
            it[name] = "R&D"
        } get Teams.id
        println(duplicateTeam.value)

        val mapping = listOf(UUID.randomUUID() to "Q%W")
        Teams.batchInsert(mapping, ignore = true) {
            this[Teams.id] = it.first
            this[Teams.name] = it.second
        }

        val yaUid = UUID.randomUUID()
        val yaTeam = Team.new(yaUid) {
            name = "Enabler"
        }
        println(yaTeam)

        SchemaUtils.drop (Teams)
    }
}

