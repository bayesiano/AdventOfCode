package day2

import java.io.File

object Day2 {
    val filename = listOf<String>( "day2/example2.txt", "day2/input2.txt")

    val f = { pos : Pair<Int,Int> -> Pair(pos.first + 2, pos.second)}
    val orders =
        filename.map {
            File(javaClass.classLoader.getResource(it).file).readLines()
                .map { input ->
                    val order = input.split(' ')
                    when( order[0]) {
                        "forward" -> Pair(order[1].toInt(),0)
                        "down" -> Pair(0,order[1].toInt())
                        "up" -> Pair(0,-order[1].toInt())
                        else -> throw Exception( "Unknown order")
                    }
                }
        }

    @JvmStatic
    fun main(args: Array<String>) {
        val pos = orders.map{ it.fold(Pair(0,0)) { acc, move -> Pair(acc.first + move.first , acc.second + move.second) }}
        pos.forEach { println( "$it - ${it.first*it.second}") }
    }

}