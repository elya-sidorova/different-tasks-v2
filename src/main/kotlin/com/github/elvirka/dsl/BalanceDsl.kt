package com.github.elvirka.dsl

import com.github.elvirka.dsl.Message.LowBalanceAlert

@Suppress("ClassName")
private object fetch {
    infix fun balance(number: Int) = println("Fetch the balance for $number")
}

enum class Message {
    StatementReady,
    LowBalanceAlert;

    infix fun to(number: Int) =
        println("sending message $this to $number")
}

@Suppress("ClassName")
object send {
    infix fun message(messageId: Message) = messageId
}

typealias account = Account

class Account(private val accountNumber: Int) {

    infix fun deposit(amount: Double) =
        println("depositing $${amount} into account $accountNumber")

    companion object {
        infix fun number(accountNumber: Int) = Account(accountNumber)
    }
}

fun main() {
    fetch balance 1234

    send message LowBalanceAlert to 12234

    account number 12345678 deposit 100.25

}