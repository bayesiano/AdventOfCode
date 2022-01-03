package day1

import java.io.File

object Day1b {
    val filename = listOf<String>( "day1/example1.txt", "day1/input1.txt")

    val measures =
        filename.map {
            File(javaClass.classLoader.getResource(it).file).readLines()
                .map { it.toInt() }
        }

    @JvmStatic
    fun main(args: Array<String>) {
        var increments =
            measures.map{ measureList ->
                measureList.windowed(3, 1).map { it.sum() }
                .windowed(2, 1)
                .map { it[0] < it[1] }
                .count { it }
            }
        println("$increments times has incremented")
    }
}