package com.github.elvirka.patternsBook

class Server private constructor(port: Long) {

    init {
        println("Server started on port $port")
    }

    companion object {
        fun withPort(port: Long) = Server(port)
    }

}

fun main() {
    Server.withPort(8080)
    //Server(8080)
}