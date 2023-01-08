package com.github.elvirka.libraries.exposed

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.countDistinct
import org.jetbrains.exposed.sql.selectAll
import kotlin.test.assertTrue

object StarWarsFilms : IntIdTable() {
    val sequelId = integer("sequel_id").uniqueIndex()
    val name = varchar("name", 50)
    val director = varchar("director", 50)
}

object Players : Table() {
    val filmId = reference("film_id", StarWarsFilms)
    //val sequelId = reference("sequel_id", StarWarsFilms.sequelId).uniqueIndex()
    val name = varchar("name", 50)
}

fun main() {
    Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

    SchemaUtils.create(StarWarsFilms, Players)

    val query = StarWarsFilms.selectAll()
    query.forEach {
        assertTrue { it[StarWarsFilms.sequelId] >= 7 }
    }

    StarWarsFilms.slice(StarWarsFilms.name, StarWarsFilms.director).selectAll()
        .forEach {
            assertTrue { it[StarWarsFilms.name].startsWith("The") }
        }

    StarWarsFilms.slice(StarWarsFilms.name.countDistinct())
}