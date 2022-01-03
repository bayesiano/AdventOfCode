package day13

import java.io.File

object Day13b {
    @JvmStatic
    fun main(args: Array<String>) {
        Coordinate( 0,0 ).apply{ assert( mirrorX( 10) == this) }
        Coordinate( 10,0 ).apply{ assert( mirrorX( 10) == null) }
        Coordinate( 11,2 ).apply{ assert( mirrorX( 10) == Coordinate( 9,2 )) }
        println(solve("day13/example13.txt"))
        println(solve("day13/input13.txt"))
    }

    data class Coordinate(val x: Int, val y: Int) {
        fun mirrorX(foldOffset: Int) =
            if (x < foldOffset) this
            else if (x > foldOffset) Coordinate(2 * foldOffset - x, y)
            else null

        fun mirrorY(foldOffset: Int) =
            if (y < foldOffset) this
            else if (y > foldOffset) Coordinate(x, 2 * foldOffset - y)
            else null


        companion object {
            fun parse(input: String) =
                input.split("\n", "\r\n").map { it.split(',').map { it.toInt() } }.map { Coordinate(it[0], it[1]) }
                    .toSet()
        }
    }

    data class Fold(val transformation: (Coordinate) -> Coordinate) {
        fun apply(coordinate: Coordinate) = transformation(coordinate)

        companion object {
           fun parse(input: String) =
                input.split("\n", "\r\n").map {
                    it.split(' ')[2].split('=').let { params ->
                        val foldOffset = params[1].toInt()
                        if (params[0] == "x") { c: Coordinate -> c.mirrorX(foldOffset) }
                        else { c: Coordinate -> c.mirrorY(foldOffset) }
                    }
                }
        }
    }

    fun solve(filename: String): Int {
        var config = File(javaClass.classLoader.getResource(filename).file)
            .readText().split("\n\n", "\r\n\r\n")
        val matrix = Coordinate.parse(config[0])
        val folds = Fold.parse(config[1])

        val res = folds.fold( matrix) { matrix, f->
            matrix.mapNotNull{ f( it)}.toSet()
        }

        //println( res.sortedBy { it.y*100 + it.x } )
        val minX = res.minOf { it.x }
        val maxX = res.maxOf { it.x }
        val minY = res.minOf { it.y }
        val maxY = res.maxOf { it.y }
        (minY..maxY).forEach { y ->
            (minX..maxX).forEach { x ->
                if( res.any { it.x == x && it.y == y }) print( '#')
                else  print( ' ')
            }
            println()
        }
        return res.size
    }
//RLBCJGLU
}


