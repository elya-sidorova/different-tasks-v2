package com.github.elvirka.patternsBook

fun main() {
    val mail1 = MailV1(
        listOf("manager@company.com"),
        null,
        "Ping",
        null,
        true
    )
    println(mail1)
    val mail = MailBuilder()
        .to(listOf("manager@company.com"))
        .title("Ping")
        .important(true)
        .build()
    println(mail)

    val mail2 = MailV2(listOf("manager@company.com"))
        .title("Ping")
        .important(true)
    println(mail2)

    val mail21 = MailV21(listOf("manager@company.com")).apply {
        title = "Ping"
        important = true
    }
    println(mail21)
    val mail3 = MailV3(
        to = listOf("manager@company.com"),
        title = "Ping",
        important = true
    )
    println(mail3)
}

data class MailV1(
    val to: List<String>,
    val cc: List<String>?,
    val title: String?,
    val message: String?,
    val important: Boolean,
)

class MailBuilder {
    private var to: List<String> = listOf()
    private var cc: List<String> = listOf()
    private var title: String = ""
    private var message: String = ""
    private var important: Boolean = false

    data class Mail internal constructor(
        val to: List<String>,
        val cc: List<String>?,
        val title: String?,
        val message: String?,
        val important: Boolean
    )

    fun build(): Mail {
        if (to.isEmpty()) {
            throw RuntimeException("To property is empty")
        }
        return Mail(to, cc, title, message, important)
    }

    fun message(message: String): MailBuilder {
        this.message = message
        return this
    }
    fun to(to: List<String>): MailBuilder {
        this.to = to
        return this
    }
    fun cc(cc: List<String>): MailBuilder {
        this.cc = cc
        return this
    }
    fun title(title: String): MailBuilder {
        this.title = title
        return this
    }
    fun important(important: Boolean): MailBuilder {
        this.important = important
        return this
    }
}

data class MailV2(
    val to: List<String>,
    private var _message: String? = null,
    private var _cc: List<String>? = null,
    private var _title: String? = null,
    private var _important: Boolean? = null
) {

    fun message(message: String) = apply {
        _message = message
    }

    fun cc(cc: List<String>) = apply {
        _cc = cc
    }

    fun title(title: String) = apply {
        _title = title
    }

    fun important(important: Boolean) = apply {
        _important = important
    }
}

data class MailV21(
    val to: List<String>,
    var message: String? = null,
    var cc: List<String>? = null,
    var title: String? = null,
    var important: Boolean? = null
)

data class MailV3(
    val to: List<String>,
    val cc: List<String> = listOf(),
    val title: String = "",
    val message: String = "",
    val important: Boolean = false
)