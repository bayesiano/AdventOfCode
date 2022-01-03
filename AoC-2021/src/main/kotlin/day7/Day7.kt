package day7

import java.io.File
import kotlin.math.abs


object Day7 {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day7/example7.txt"))
        println( solve("day7/input7.txt"))
    }

    fun solve( filename: String): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .flatMap { it.split(",").map { it.toInt() } }.sorted()
        //println(data)

        val pos = data[data.size/2]
        //println( pos)
        val cost = data.sumOf { Math.abs( it - pos ) }
        return cost
    }
}