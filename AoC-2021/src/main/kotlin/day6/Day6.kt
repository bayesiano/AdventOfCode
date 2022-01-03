package day6

import java.io.File


object Day6 {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day6/example6.txt", 18))
        println( solve("day6/example6.txt", 80))
        println( solve("day6/input6.txt", 80))
        //println( solve("day6/input6.txt", 256))
    }

    fun solve(filename: String, numDays: Int): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .flatMap { it.split(",").map { it.toInt() } }
        //println(data)

        (1..numDays).forEach { day ->
            data += data.filter { it == 0 }.map { 9 }
            data = data.map { if( it == 0) 6 else it - 1 }
            //println( "$day ${data.size}" )
        }
        return data.size
    }
    fun solve2(filename: String, numDays: Int): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .flatMap { it.split(",").map { it.toInt() } }
        //println(data)
        return data.size
    }
}