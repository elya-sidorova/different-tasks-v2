package com.github.elvirka.hyperskill

import kotlin.math.min
import kotlin.math.max

fun main() {
    println("Connect Four")
    println("First player's name:")
    val player1 = Player(name = readln(), 'o')
    println("Second player's name:")
    val player2 = Player(name = readln(), '*')
    val board = readBoardDimensions()
    val roundsCount = readRoundsCount()
    val game = Game(
        player1 = player1,
        player2 = player2,
        board = board,
        roundsCount = roundsCount
    )
    val boardPrinter = BoardPrinter(board)
    val gamePrinter = GamePrinter(game)
    gamePrinter.printPlayers()
    boardPrinter.printSize()
    gamePrinter.printRoundsCount()

    (1..game.roundsCount).map { roundNumber ->
        gamePrinter.printRoundNumber(roundNumber)
        boardPrinter.printBoard()

        runGameRound(game, gamePrinter, boardPrinter)

        gamePrinter.printResult()
        if (roundNumber == roundsCount) {
            gamePrinter.printEndGame()
        } else {
            game.board.clear()
            game.nextStartingPlayer()
        }
    }

}

fun readRoundsCount(): Int {
    do {
        println("Do you want to play single or multiple games?")
        println("For a single game, input_1.txt 1 or press Enter")
        println("Input a number of games:")
        val roundsCountInput = readln().ifEmpty { null }
        if (roundsCountInput == null) {
            return 1
        } else {
            val roundsCount = roundsCountInput.toIntOrNull()
            if (roundsCount == null || roundsCount == 0) {
                printInvalidInput()
            } else {
                return roundsCount
            }
        }
    } while (true)
}

fun readBoardDimensions(): Board {
    do {
        println("Set the board dimensions (Rows x Columns)")
        println("Press Enter for default (6 x 7)")
        val dimensionsInput = readln()
        val board = BoardParser.parse(dimensionsInput)
        board?.let { return board }
    } while (true)
}

fun printInvalidInput() {
    println("Invalid input_1.txt")
}

fun runGameRound(game: Game, gamePrinter: GamePrinter, boardPrinter: BoardPrinter) {
    do {
        println("${game.currentPlayer.name}'s turn:")
        when (val playerCommand = PlayerCommandParser.parse(readln(), game)) {
            PlayerCommand.EndGame -> {
                gamePrinter.printEndGame()
                return
            }

            is PlayerCommand.Move -> {
                if (!game.isBoardColumnFull(playerCommand.column)) {
                    val row = game.addMove(playerCommand.column, game.currentPlayer)
                    boardPrinter.printBoard()
                    if (game.isBordFull()) {
                        println("It is a draw")
                        game.player1.addPoints(GameResult.DRAW)
                        game.player2.addPoints(GameResult.DRAW)
                        return
                    } else {
                        val result = game.checkWinningCondition(row, playerCommand.column, game.currentPlayer)
                        if (result) {
                            println("Player ${game.currentPlayer.name} won")
                            game.currentPlayer.addPoints(GameResult.WIN)
                            return
                        }
                    }
                    game.nextPlayer()
                } else {
                    println("Column ${playerCommand.column} is full")
                }
            }

            null -> continue
        }
    } while (true)
}

data class Game(
    val player1: Player,
    val player2: Player,
    val board: Board,
    val roundsCount: Int,
) {
    val multipleGameMode: Boolean
        get() = roundsCount > 1

    private var startingPlayer = player1

    fun nextStartingPlayer() {
        startingPlayer = if (startingPlayer == player1) player2 else player1
        currentPlayer = startingPlayer
    }

    var currentPlayer = startingPlayer
        private set

    fun nextPlayer() {
        currentPlayer = if (currentPlayer == player1) player2 else player1
    }

    fun isValidBoardColumn(columnNum: Int): Boolean =
        columnNum in 1..board.columnsCount

    fun isBoardColumnFull(columnNum: Int): Boolean {
        val columnIdx = columnNum.toIndex()
        return board[0][columnIdx] != null
    }

    fun isBordFull(): Boolean = board[0].all { it != null }

    fun addMove(columnNum: Int, player: Player): Int {
        val columnIdx = columnNum.toIndex()
        var rowNum: Int? = null
        for (rowIdx in board.lastIndex downTo 0) {
            if (board.trySet(rowIdx, columnIdx, player)) {
                rowNum = rowIdx.toSerialNumber()
                break
            }
        }
        return rowNum ?: error("Free cell not found.")
    }

    fun checkWinningCondition(rowNum: Int, columnNum: Int, player: Player): Boolean {
        val rowIdx = rowNum.toIndex()
        val columnIdx = columnNum.toIndex()
        return board.checkRow(rowIdx, player, winningCondition)
            || board.checkColumn(columnIdx, player, winningCondition)
            || board.checkPrimaryDiagonal(rowIdx, columnIdx, player, winningCondition)
            || board.checkSecondaryDiagonal(rowIdx, columnIdx, player, winningCondition)
    }

    private companion object {
        const val winningCondition = 4

        fun Int.toIndex(): Int = this - 1
        fun Int.toSerialNumber(): Int = this + 1
    }
}

class GamePrinter(
    private val game: Game,
) {
    fun printPlayers() {
        println("${game.player1.name} VS ${game.player2.name}")
    }

    fun printRoundsCount() {
        if (game.multipleGameMode) {
            println("Total ${game.roundsCount} games")
        } else {
            println("Single game")
        }
    }

    fun printRoundNumber(roundNumber: Int) {
        if (game.multipleGameMode) {
            println("Game #$roundNumber")
        }
    }

    fun printResult() {
        if (game.multipleGameMode) {
            println("Score")
            with(game) {
                println("${player1.name}: ${player1.score} ${player2.name}: ${player2.score}")
            }
        }
    }

    fun printEndGame() {
        println("Game over!")
    }
}

data class Player(
    val name: String,
    val character: Char,
) {
    var score: Int = 0

    fun addPoints(result: GameResult) {
        score += result.points
    }
}

enum class GameResult(val points: Int) {
    WIN(2), DRAW(1)
}

sealed class PlayerCommand {
    object EndGame : PlayerCommand()
    class Move(val column: Int) : PlayerCommand() {

        companion object {
            fun ofOrNull(column: Int, game: Game): Move? =
                if (game.isValidBoardColumn(column)) {
                    Move(column)
                } else {
                    println("The column number is out of range (1 - ${game.board.columnsCount})")
                    null
                }
        }
    }
}

object PlayerCommandParser {
    private const val END_MOVE = "end"

    fun parse(input: String, game: Game): PlayerCommand? =
        if (input == END_MOVE) {
            PlayerCommand.EndGame
        } else {
            val column = input.toIntOrNull()
            if (column == null) {
                println("Incorrect column number")
                null
            } else {
                PlayerCommand.Move.ofOrNull(column, game)
            }
        }
}

class Board private constructor(
    private val grid: MutableList<MutableList<Player?>>,
) : List<List<Player?>> by grid {
    val rowsCount: Int get() = grid.size
    val columnsCount: Int get() = grid.first().size

    val rowsLastIndex: Int get() = rowsCount - 1
    val columnsLastIndex: Int get() = columnsCount - 1

    fun clear() {
        grid.map {  row ->
            row.fill(null)
        }
    }

    fun trySet(rowIdx: Int, columnIdx: Int, player: Player): Boolean =
        if (grid[rowIdx][columnIdx] == null) {
            grid[rowIdx][columnIdx] = player
            true
        } else false

    fun checkRow(rowIdx: Int, player: Player, count: Int): Boolean {
        var counter = 0
        for (cell in grid[rowIdx]) {
            if (cell == player) {
                counter++
                if (counter == count) {
                    return true
                }
            } else {
                counter = 0
            }
        }
        return false
    }

    fun checkColumn(columnIdx: Int, player: Player, count: Int): Boolean {
        var counter = 0
        for (rowIdx in grid.indices) {
            if (grid[rowIdx][columnIdx] == player) {
                counter++
                if (counter == count) {
                    return true
                }
            } else {
                counter = 0
            }
        }
        return false
    }

    fun checkPrimaryDiagonal(
        rowIdx: Int, columnIdx: Int, player: Player, count: Int
    ): Boolean {
        var counter = 0
        var c = max(columnIdx - rowIdx, 0)
        var r = max(rowIdx - columnIdx, 0)
        val length = min(rowsCount, columnsCount) - max(r, c)
        for (i in 1..length) {
            if (grid[r][c] == player) {
                counter++
                if (counter == count) {
                    return true
                }
            } else {
                counter = 0
            }
            r++; c++
        }
        return false
    }

    fun checkSecondaryDiagonal(
        rowIdx: Int, columnIdx: Int, player: Player, count: Int
    ): Boolean {
        var counter = 0
        var r = max(rowIdx + columnIdx - rowsLastIndex, 0)
        var c = min(rowIdx + columnIdx, columnsLastIndex)
        val length = min(rowIdx + columnIdx + 1, rowsCount - r)
        for (i in 1..length) {
            if (grid[r][c] == player) {
                counter++
                if (counter == count) {
                    return true
                }
            } else {
                counter = 0
            }
            r++; c--
        }
        return false
    }

    companion object {
        val default = ofOrNull(rowsCount = 6, columnsCount = 7)

        fun ofOrNull(rowsCount: Int, columnsCount: Int): Board? {
            val dimensionsRange = 5..9
            return if (rowsCount !in dimensionsRange) {
                with(dimensionsRange) {
                    println("Board rows should be from $first to $last")
                }
                null
            } else if (columnsCount !in dimensionsRange) {
                with(dimensionsRange) {
                    println("Board columns should be from $first to $last")
                }
                null
            } else {
                val grid: MutableList<MutableList<Player?>> = MutableList(rowsCount) {
                    MutableList(columnsCount) { null }
                }
                Board(grid)
            }
        }
    }
}

object BoardParser {
    private val dimensionsRegex = """^(\d+)\s*[xX]\s*(\d+)$""".toRegex()

    fun parse(input: String): Board? {
        val trimmedInput = input.trim()
        if (trimmedInput.isEmpty()) {
            return Board.default
        }
        val values = dimensionsRegex.find(trimmedInput)?.groupValues
        return if (values == null) {
            printInvalidInput()
            null
        } else {
            Board.ofOrNull(rowsCount = values[1].toInt(), columnsCount = values[2].toInt())
        }
    }
}

class BoardPrinter(
    private val board: Board,
) {
    private val borderCount = board.columnsCount + 1

    fun printSize() {
        println("${board.rowsCount} X ${board.columnsCount} board")
    }

    fun printBoard() {
        printColumnNumbers()
        for (rowIdx in 0..board.rowsLastIndex) (
            println(border + board[rowIdx].map { it?.character ?: space }.joinToString(border) + border)
            )
        printBottom()
    }

    private fun printBottom() {
        val middleParts = List(borderCount - 2) { "═╩" }.joinToString(empty)
        println(
            "╚$middleParts═╝"
        )
    }

    private fun printColumnNumbers() {
        val numbers = (1..board.columnsCount).joinToString(space)
        println(" $numbers")
    }

    companion object {
        const val border = "║"
        const val space = " "
        const val empty = ""
    }
}