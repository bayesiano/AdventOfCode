package day4

import sun.security.util.BitArray
import java.io.File

object Day4b {
    data class State(var row: Int, var col: Int, var aim: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day4/example4.txt"))
        println( solve("day4/input4.txt"))
    }

    fun solve(filename: String): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readText()
            .trim()
            .split("\n\n", "\r\n\r\n")

        val numbersDrawn = data[0].split(',').map { it.toInt() }

        var boards = getBoards( data.drop(1)).toMutableList()

        numbersDrawn.forEach { n ->
            val winners = boards.mapIndexedNotNull { index, board ->  if( board.mark( n)) index else null}.sortedDescending()
            //println( winners)
            val removed = winners.map {
                boards.removeAt( it)
            }
            if( boards.isEmpty()) {
                val unmarkedNumbers = removed.first().unmarkedNumbers
                //println( "$winners, $unmarkedNumbers")
                //println( n * unmarkedNumbers.sum())
                return n * unmarkedNumbers.sum()
            }
        }
        return -1
    }

    class Board( boardRows: List<List<Int>>) {
        val unmarkedNumbers: List<Int> get() = numbers.filterIndexed { i, n -> !marked[i]}
        val numRows = boardRows.size
        val numCols = boardRows[0].size
        private val numbers = boardRows.flatten()
        private val marked = BitArray( numbers.size)

        fun mark(n: Int): Boolean {
            val indexes = numbers.mapIndexedNotNull{ i, v-> if ( v == n) i else null }
            indexes.forEach{ i-> marked[i] = true }
            (0 until numRows).forEach{ row ->
                val offset = row * numCols
                if( (0 until numCols).all { col -> marked[offset + col] }) return true
            }
            (0 until numCols).forEach{ col ->
                if( (0 until numRows).all { row -> marked[ row*numCols + col]}) return true
            }
            return false
        }
    }
    fun getBoards( input: List<String>): List<Board> {
            val boards = input.map { line ->
                val boardStr = line.split("\n", "\r\n")
                val boardRows = boardStr.map { it.split(Regex(" +")).filter { it.isNotBlank() }.map { it.toInt() } }
                Board( boardRows)
            }
        return boards
    }
}