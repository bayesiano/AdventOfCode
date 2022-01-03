package day1

import java.io.File

object Day1 {
    //val filename = "day1/example1.txt"
    val filename = "day1/input1.txt"

    val measures =
        File(javaClass.classLoader.getResource(filename).file).readLines()
            .map { it.toInt() }

    @JvmStatic
    fun main(args: Array<String>) {
        var increments = 0
        var lastMeasure = measures.first()
        measures.subList( 1, measures.size).map{
            val increase = lastMeasure < it
            if( increase) increments++
            lastMeasure = it
            println( "$it - $increase")
        }
        println( "$increments times has incremented")
    }
}