package day9

import java.io.File


object Day9 {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day9/example9.txt"))
        println( solve("day9/input9.txt"))
    }

    fun solve( filename: String): Int {
        var map = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.toCharArray().map { it.digitToInt() }.toIntArray()}.toTypedArray()
        //println(map)

        val lowPointsHeigth = mutableListOf<Int>()
        map.indices.forEach { y ->
            map[0].indices.forEach { x ->
                if( lowerThanNeigbours( x, y, map)) lowPointsHeigth += map[y][x]
            }
        }
        //println( lowPointsHeigth)
        return lowPointsHeigth.sumOf { it + 1 }
    }

    private fun lowerThanNeigbours( x: Int, y: Int, map: Array<IntArray>): Boolean {
        val v = map[y][x]
        if( x > 0 && map[y][x-1] <= v) return false
        if( x < map[0].size-1 && map[y][x+1] <= v) return false
        if( y > 0 && map[y-1][x] <= v) return false
        if( y < map.size-1 && map[y+1][x] <= v) return false
        return true
    }
}