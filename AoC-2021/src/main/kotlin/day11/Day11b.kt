package day11

import java.io.File


object Day11b {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day11/example11.txt", 195))
        println(solve("day11/input11.txt"))
    }

    fun solve(filename: String, maxIncs: Int = Int.MAX_VALUE): Int {
        var map = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.toCharArray().map { it.digitToInt() }.toIntArray() }.toTypedArray()

        var flashes = 0
        var inc = 0
        while( flashes < map.size * map[0].size) {
            flashes = inc( map)
            inc++
        }

        //println()
        //println( "$maxIncs inc")
        return inc
    }

    val neighbours = listOf(
        Pair( -1, -1),
        Pair( -1, 0),
        Pair( -1, 1),
        Pair( 0, -1),
        Pair( 0, 0),
        Pair( 0, 1),
        Pair( 1, -1),
        Pair( 1, 0),
        Pair( 1, 1),
    )
    private fun inc( map: Array<IntArray>): Int {
        var flashes = 0
        map.indices.forEach { y ->
            map[0].indices.forEach { x ->
                flashes += review( y, x, map)
            }
        }
        map.indices.forEach { y ->
            map[0].indices.forEach { x ->
                if( map[y][x] < 0) map[y][x] = 0
            }
        }

        return flashes
    }

    private fun review( y: Int, x: Int, map: Array<IntArray>): Int {
        if( x < 0 || x >= map[0].size) return 0
        if( y < 0 || y >= map.size) return 0
        var flashes = 0
        map[y][x]++
        if( map[y][x] >= 10) {
            flashes++
            map[y][x] = Int.MIN_VALUE
            neighbours.forEach { n ->
                //map[y + n.first][x + n.second]++
                flashes += review(y + n.first, x + n.second, map)
            }
        }
        return flashes
    }

    private fun printMap(map: Array<IntArray>) {
        map.forEach { row ->
            row.forEach { print( it) }
            println()
        }

    }
}


