package day14

import java.io.File


object Day14 {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day14/example14.txt", 10) )
        println(solve("day14/input14.txt", 10) )
    }

    fun solve(filename: String, steps: Int): Int {
        var config = File(javaClass.classLoader.getResource(filename).file)
            .readText().split("\n\n", "\r\n\r\n")
        val polymer = config[0].toList()
        val changes = config[1].split("\n", "\r\n").map{ it.split( " -> ").let{ it[0] to it[1].first() } }.toMap()

        val res = (1..steps).fold( polymer) { p, step -> process( p, changes) /*.also { println( "$step - $it")}*/ }

        val totals = res.groupingBy { it }.eachCount()
        return totals.values.maxOf { it } - totals.values.minOf { it }
    }

    private fun process( p: List<Char>, changes: Map<String,Char>): List<Char> =
        p.windowed( 2, 1, false).flatMap{ listOf<Char>( it[0], changes[it.joinToString("")] ?: ' ') } + p.last()


}


