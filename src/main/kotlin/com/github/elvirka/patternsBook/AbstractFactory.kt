package com.github.elvirka.patternsBook

fun main() {
    val server = ConfigParser.server(listOf("port: 8080", "environment: production"))
    println(server)
    val port: Int? = server.properties[0].value as? Int
    println(port)
}

interface Property {
    val name: String
    val value: Any
}

interface ServerConfiguration {
    val properties: List<Property>
}

data class IntProperty(
    override val name: String,
    override val value: Int
) : Property

data class StringProperty(
    override val name: String,
    override val value: String
) : Property

data class ServerConfigurationImpl(
    override val properties: List<Property>
) : ServerConfiguration


class ConfigParser {
    companion object {

        fun property(prop: String): Property {
            val (name, value) = prop.split(":")
            return when (name) {
                "port" -> IntProperty(name, value.trim().toInt())
                "environment" -> StringProperty(name, value.trim())
                else -> throw RuntimeException("Unknown property: $name")
            }
        }

        fun server(propertyStrings: List<String>): ServerConfiguration {
            val parsedProperties = mutableListOf<Property>()
            for (p in propertyStrings) {
                parsedProperties += property(p)

            }
            return ServerConfigurationImpl(parsedProperties)
        }
    }
}