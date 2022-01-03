package day11

import java.io.File


object Day11 {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day11/example11.txt", 1))
        println(solve("day11/example11.txt", 2))
        println(solve("day11/example11.txt", 3))
        println(solve("day11/example11.txt", 4))
        println(solve("day11/example11.txt", 5))
        println(solve("day11/example11.txt", 6))
        println(solve("day11/example11.txt", 7))
        println(solve("day11/example11.txt", 8))
        println(solve("day11/example11.txt", 9))
        println(solve("day11/example11.txt", 10))
        println(solve("day11/example11.txt", 20))
        println(solve("day11/example11.txt", 100))
        println(solve("day11/input11.txt", 100))
    }

    fun solve(filename: String, numDays: Int = 1): Int {
        var map = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.toCharArray().map { it.digitToInt() }.toIntArray() }.toTypedArray()

        val flashes = (1..numDays).map{ inc( map) }.sum()
        //println()
        //println( "$numDays inc")
        //printMap( map )

        return flashes
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


