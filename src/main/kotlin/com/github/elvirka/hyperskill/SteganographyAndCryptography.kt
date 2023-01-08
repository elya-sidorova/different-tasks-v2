package com.github.elvirka.hyperskill

fun main() {
    do {
        val task = ConsoleReaderWithRetry(
            parsableClass = EncryptionTask,
            promptMessage = "Task ${EncryptionTask.valuesToString()}:",
            errorMessage = "Wrong task:"
        ).read()

        when(task) {
            EncryptionTask.Hide -> println("Hiding message in image.")
            EncryptionTask.Show -> println("Obtaining message from image.")
            EncryptionTask.Exit -> println("Bye!")
        }

    } while (task != EncryptionTask.Exit)
}

private enum class EncryptionTask {
    Hide, Show, Exit;

    companion object : Parsable<EncryptionTask>, PrintableEnum {
        override fun parse(input: String): EncryptionTask? {
            return enumFromString(input, EncryptionTask::name)
        }

        override fun valuesToString(): String {
            return enumValuesToString(lowercase = true, EncryptionTask::name)
        }
    }
}