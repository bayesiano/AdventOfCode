package day9

import java.io.File


object Day9b {
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

        val lowPointsHeigth = mutableListOf<Pair<Int, Int>>()
        map.indices.forEach { y ->
            map[0].indices.forEach { x ->
                lowerThanNeigbours( x, y, map)?.let { lowPointsHeigth += it}
            }
        }
        //println( lowPointsHeigth)
        val largestBasins = lowPointsHeigth.map { calculateBasinSize( it, map) }
        //println( largestBasins)
        return largestBasins.sortedDescending().take(3).reduce { acc, v -> acc * v }
    }

    private fun calculateBasinSize( pos: Pair<Int, Int>, map: Array<IntArray>): Int {
        val visited = Array(map.size) { BooleanArray( map[0].size) }
        return moveUntil9( pos.first, pos.second, map, visited)
    }

    private fun moveUntil9( x: Int, y: Int, map: Array<IntArray>, visited: Array<BooleanArray> ): Int {
        if( x < 0 || x >= map[0].size) return 0
        if( y < 0 || y >= map.size) return 0
        if( map[y][x] == 9 || visited[y][x]) return 0
        visited[y][x] = true
        return 1 +
                        moveUntil9( x - 1, y, map, visited) +
                        moveUntil9( x + 1, y, map, visited) +
                        moveUntil9( x, y - 1, map, visited) +
                        moveUntil9( x, y + 1, map, visited)
    }

    private fun lowerThanNeigbours( x: Int, y: Int, map: Array<IntArray>): Pair<Int, Int>? {
        val v = map[y][x]
        if( x > 0 && map[y][x-1] <= v) return null
        if( x < map[0].size-1 && map[y][x+1] <= v) return null
        if( y > 0 && map[y-1][x] <= v) return null
        if( y < map.size-1 && map[y+1][x] <= v) return null
        return Pair(x,y)
    }
}