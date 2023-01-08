package com.github.elvirka.patternsBook

fun main() {
    val starTrekRepository = DefaultStarTrekRepository()
    val withValidating = ValidatingAddCaptain(starTrekRepository)
    val withLoggingAndValidating = LoggingGetCaptain(withValidating)

    withLoggingAndValidating["USS Enterprise"]
    withLoggingAndValidating["USS Voyager"] = "Kathryn Janeway"
}

interface StarTrekRepository {
    operator fun get(starshipName: String): String
    operator fun set(starshipName: String, captainName: String)
}

open class DefaultStarTrekRepository : StarTrekRepository {
    private val starshipCaptains = mutableMapOf(
        "USS Enterprise" to "Jean-Luc Picard"
    )

    override fun get(starshipName: String): String {
        return starshipCaptains[starshipName] ?: "Unknown"
    }

    override fun set(starshipName: String, captainName: String) {
        starshipCaptains[starshipName] = captainName
    }
}

class LoggingGetCaptain(
    private val repository: StarTrekRepository,
) : StarTrekRepository by repository {
    override fun get(starshipName: String): String {
        println("Getting captain for $starshipName")
        return repository[starshipName]
    }
}

class ValidatingAddCaptain(
    private val repository: StarTrekRepository,
) : StarTrekRepository by repository {
    private val maxNameLength = 15
    override fun set(starshipName: String, captainName: String) {
        require(captainName.length < maxNameLength) {
            "$captainName is longer than $maxNameLength characters!"
        }
        repository[starshipName] = captainName
    }
}