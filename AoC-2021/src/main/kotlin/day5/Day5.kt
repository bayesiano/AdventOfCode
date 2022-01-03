package day5

import java.io.File

object Day5 {
    data class State(var row: Int, var col: Int, var aim: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day5/example5.txt"))
        println( solve("day5/input5.txt"))
    }
    data class Point2D(val x: Int, val y: Int) {
        companion object {
            fun parsePoint(str: String): Point2D = with( str.split(",")) {
                Point2D( first().toInt(), last().toInt())
            }
        }
    }
    fun solve(filename: String): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.split(" -> ").map { Point2D.parsePoint(it) } }
        //println(data)

        val maxRow = data.maxOf { it.maxOf { it.x} }
        val maxCol = data.maxOf { it.maxOf { it.y} }
        val map = Array(maxRow+1) { Array( maxCol+1) { 0} }

        data.filter { it.first().x == it.last().x || it.first().y == it.last().y }
            .forEach { vector ->
                val incX = vector.first().x - vector.last().x
                val incY = vector.first().y - vector.last().y
                val dX = if( incX < 0) 1 else if( incX > 0) -1 else 0
                val dY = if( incY < 0) 1 else if( incY > 0) -1 else 0
                var x = vector.first().x
                var y = vector.first().y
                map[y][x]++
                while( x != vector.last().x || y != vector.last().y) {
                    x += dX
                    y += dY
                    map[y][x]++
                    //println("$x,$y  $dX,$dY")
                }
        }
        /*map.forEach {
            it.forEach { if( it == 0) print( " .") else print( "% 2d".format( it)) }
            println()
        }*/
        return map.sumOf { it.count { it > 1 } }
    }
    //4993
}