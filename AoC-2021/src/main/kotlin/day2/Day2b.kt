package day2

import java.io.File

object Day2b {
    val filename = listOf<String>( "day2/example2.txt", "day2/input2.txt")

    val f = { pos : Pair<Int,Int> -> Pair(pos.first + 2, pos.second)}

    val orders =
        filename.map {
            File(javaClass.classLoader.getResource(it).file).readLines()
                .map { input ->
                    val order = input.split(' ')
                    when( order[0]) {
                        "forward" -> { s: State -> State( s.row+order[1].toInt(), s.col + s.aim*order[1].toInt(), s.aim) }
                        "down" -> { s: State -> State( s.row, s.col, s.aim+order[1].toInt()) }
                        "up" -> { s: State -> State( s.row, s.col, s.aim+-order[1].toInt()) }
                        else -> throw Exception( "Unknown order")
                    }
                }
        }

    data class State( var row: Int, var col:Int, var aim: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        val pos = orders.map{
            it.fold( State(0,0, 0)) { acc, move ->
                move( acc)
            }
        }
        pos.forEach { println( "$it - ${it.row * it.col}")}
    }

}