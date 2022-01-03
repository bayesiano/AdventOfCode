package day8

import java.io.File


object Day8 {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day8/example8.txt"))
        println( solve("day8/input8.txt"))
    }

    fun solve( filename: String): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.split(" | ")[1] }
        //println(data)

        val combinations = data.map { it.split( ' ') }
        val combLengths = combinations.flatMap { it.map { it.length } }
        val comb1478 = combLengths.filter { it in listOf( 2,3,4,7 ) }

        return comb1478.size
    }
}