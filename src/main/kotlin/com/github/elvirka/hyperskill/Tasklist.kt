package com.github.elvirka.hyperskill

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.until
import java.io.File
import java.lang.RuntimeException
import java.time.LocalTime
import java.time.Month

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(LocalDate::class.java, LocalDateAdapter().nullSafe())
    .add(LocalTime::class.java, LocalTimeAdapter().nullSafe())
    .build()
private val type = Types.newParameterizedType(
    List::class.java, Task::class.java, LocalDate::class.java
)
private val adapter = moshi.adapter<List<Task>>(type)

fun main() {
    val jsonFile = File("tasklist.json")
    val initJson = if (jsonFile.exists()) {
        jsonFile.readText()
    } else ""
    val taskList = if (initJson.isNotEmpty()) {
        TaskList(adapter.fromJson(initJson))
    } else {
        TaskList()
    }
    do {
        val action = ConsoleReaderWithRetry(
            parsableClass = Action,
            promptMessage = "Input an action ${Action.valuesToString()}:",
            errorMessage = "The input action is invalid"
        ).read()
        when (action) {
            Action.Add -> taskList.addTask()
            Action.Print -> taskList.printer.print()
            Action.Delete -> taskList.deleteTask()
            Action.Edit -> taskList.editTask()
            Action.End -> println("Tasklist exiting!")
        }
    } while (action != Action.End)
    val json = adapter.toJson(taskList.list())
    jsonFile.writeText(json)
}

private class TaskList(
    initTaskList: List<Task>? = null
) {
    private val list = initTaskList?.toMutableList() ?: mutableListOf()
    val printer = TaskListPrinter()

    fun list() = list.toList()

    fun editTask() {
        printer.print()
        if (list.isEmpty()) return
        val taskNumber = readTaskNumber()
        val field = ConsoleReaderWithRetry(
            parsableClass = Field,
            promptMessage = "Input a field to edit ${Field.valuesToString()}:",
            errorMessage = "Invalid field"
        ).read()
        val task = list[taskNumber.toIndex()]
        val newTask = when (field) {
            Field.Priority -> {
                val priority = readPriority()
                task.copy(priority = priority)
            }

            Field.Date -> {
                val date = readDate()
                task.copy(date = date)
            }

            Field.Time -> {
                val time = readTime()
                task.copy(time = time)
            }

            Field.Task -> {
                val text = readText()
                if (text.isNotEmpty()) {
                    task.copy(text = text)
                } else {
                    task
                }
            }
        }
        list[taskNumber.toIndex()] = newTask
        println("The task is changed")
    }

    fun deleteTask() {
        printer.print()
        if (list.isEmpty()) return
        val taskNumber = readTaskNumber()
        list.removeAt(taskNumber.toIndex())
        println("The task is deleted")
    }

    fun addTask() {
        val priority = readPriority()
        val date = readDate()
        val time = readTime()
        val text = readText()

        if (text.isNotEmpty()) {
            list.add(Task(priority, date, time, text))
        }
    }

    private fun readText(): String {
        println("Input a new task (enter a blank line to end):")
        val taskText = buildString {
            do {
                val taskLine = readln().trim()
                if (taskLine.isNotEmpty()) {
                    appendLine(taskLine)
                }
            } while (taskLine != "")
        }.trim()
        if (taskText.isEmpty()) {
            println("The task is blank")
        }
        return taskText
    }

    private fun readTaskNumber(): Int =
        ConsoleReaderWithRetry(
            parsableClass = ParsableInt,
            promptMessage = "Input the task number (1-${list.size}):",
            errorMessage = "Invalid task number",
            validator = { it.value in 1..list.size }
        ).read().value

    private fun readPriority(): Priority =
        ConsoleReaderWithRetry(
            parsableClass = Priority,
            promptMessage = "Input the task priority ${Priority.valuesToString()}:"
        ).read()

    private fun readDate(): LocalDate =
        ConsoleReaderWithRetry(
            parsableClass = ParsableDate,
            promptMessage = "Input the date (yyyy-mm-dd):",
            errorMessage = "The input date is invalid"
        ).read().value

    private fun readTime(): LocalTime =
        ConsoleReaderWithRetry(
            parsableClass = ParsableTime,
            promptMessage = "Input the time (hh:mm):",
            errorMessage = "The input time is invalid"
        ).read().value

    inner class TaskListPrinter {

        fun print() {
            if (list.isEmpty()) {
                println("No tasks have been input")
            } else {
                printHeader()
                list.mapIndexed { index, task ->
                    val number = index + 1
                    printTask(number, task)
                }
            }
        }

        private fun printTask(number: Int, task: Task) {
            val numberView = if (number < 10) "$number " else "$number"
            with(task) {
                val priorityView = when (priority) {
                    Priority.Critical -> Colors.Red
                    Priority.High -> Colors.Yellow
                    Priority.Normal -> Colors.Green
                    Priority.Low -> Colors.Blue
                }.code + " ${Colors.None.code}"

                val dueStatusView = when (dueStatus) {
                    DueStatus.InTime -> Colors.Green
                    DueStatus.Today -> Colors.Yellow
                    DueStatus.Overdue -> Colors.Red
                }.code + " ${Colors.None.code}"

                val textView = text.lines().flatMap { line ->
                    if (line.length > LineMaxLength) {
                        line.chunked(LineMaxLength)
                    } else {
                        listOf(line)
                    }
                }.map { line ->
                    if (line.length < LineMaxLength) {
                        line + " ".repeat(LineMaxLength - line.length)
                    } else {
                        line
                    }
                }
                textView.mapIndexed { index, line ->
                    if (index == 0) {
                        println(
                            "| $numberView | $date | $time | $priorityView | $dueStatusView |$line|"
                        )
                    } else {
                        println(
                            "|    |            |       |   |   |$line|"
                        )
                    }
                }
            }
            printHorizontalBorder()
        }

        private fun printHorizontalBorder() {
            println(
                "+----+------------+-------+---+---+--------------------------------------------+"
            )
        }

        private fun printHeader() {
            printHorizontalBorder()
            println(
                "| N  |    Date    | Time  | P | D |                   Task                     |"
            )
            printHorizontalBorder()
        }
    }

    private companion object {
        fun Int.toIndex(): Int = this - 1

        const val LineMaxLength = 44

        enum class Colors(val code: String) {
            Red("\u001B[101m"),
            Yellow("\u001B[103m"),
            Green("\u001B[102m"),
            Blue("\u001B[104m"),
            None("\u001B[0m");
        }
    }
}

private data class Task(
    val priority: Priority,
    val date: LocalDate,
    val time: LocalTime,
    val text: String,
) {
    val dueStatus: DueStatus
        get() {
            val currentDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
            val daysUntil = currentDate.until(date, DateTimeUnit.DAY)
            val status = when {
                daysUntil == 0 -> DueStatus.Today
                daysUntil > 0 -> DueStatus.InTime
                else -> DueStatus.Overdue
            }
            return status
        }
}

@JvmInline
private value class ParsableDate(val value: LocalDate) {
    companion object : Parsable<ParsableDate> {
        override fun parse(input: String): ParsableDate? =
            try {
                val values = input.split("-", limit = 3).map(String::toInt)
                val date = LocalDate(
                    year = values[0], monthNumber = values[1], dayOfMonth = values[2]
                )
                ParsableDate(date)
            } catch (e: RuntimeException) {
                null
            }
    }
}

@JvmInline
private value class ParsableTime(val value: LocalTime) {
    companion object : Parsable<ParsableTime> {
        override fun parse(input: String): ParsableTime? =
            try {
                val values = input.split(":", limit = 2).map(String::toInt)
                val time = LocalTime.of(values[0], values[1])
                ParsableTime(time)
            } catch (e: RuntimeException) {
                null
            }
    }
}

@JvmInline
private value class ParsableInt(val value: Int) {
    companion object : Parsable<ParsableInt> {
        override fun parse(input: String): ParsableInt? =
            input.toIntOrNull()?.let { ParsableInt(it) }
    }
}

private enum class Action {
    Add, Print, Edit, Delete, End;

    companion object : Parsable<Action>, PrintableEnum {
        override fun parse(input: String): Action? {
            return enumFromString(input, Action::name)
        }

        override fun valuesToString(): String {
            return enumValuesToString(lowercase = true, Action::name)
        }
    }
}

private enum class Priority(val code: String) {
    Critical("C"), High("H"), Normal("N"), Low("L");

    companion object : Parsable<Priority>, PrintableEnum {
        override fun parse(input: String): Priority? {
            return enumFromString(input, Priority::code)
        }

        override fun valuesToString(): String {
            return enumValuesToString(lowercase = false, Priority::code)
        }
    }
}

private enum class Field {
    Priority, Date, Time, Task;

    companion object : Parsable<Field>, PrintableEnum {
        override fun parse(input: String): Field? {
            return enumFromString(input, Field::name)
        }

        override fun valuesToString(): String {
            return enumValuesToString(lowercase = true, Field::name)
        }
    }
}

private enum class DueStatus(val code: String) {
    InTime("I"), Today("T"), Overdue("O");
}

private class LocalDateAdapter : JsonAdapter<LocalDate>(){
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        value?.let { writer.value("${it.year}-${it.monthNumber}-${it.dayOfMonth}") }

    }
    override fun fromJson(reader: JsonReader): LocalDate? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            val date = reader.nextString()
                .split("-", limit = 3)
                .map { it.toInt() }
            LocalDate(year = date[0], month = Month.of(date[1]), dayOfMonth = date[2])
        } else {
            reader.nextNull<Any>()
            null
        }
    }
}

private class LocalTimeAdapter : JsonAdapter<LocalTime>(){
    override fun toJson(writer: JsonWriter, value: LocalTime?) {
        value?.let { writer.value("${it.hour}:${it.minute}") }

    }
    override fun fromJson(reader: JsonReader): LocalTime? {
        return if (reader.peek() != JsonReader.Token.NULL) {
            val time = reader.nextString()
                .split(":", limit = 2)
                .map { it.toInt() }
            LocalTime.of(time[0], time[1])
        } else {
            reader.nextNull<Any>()
            null
        }
    }
}