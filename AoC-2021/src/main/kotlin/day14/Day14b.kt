package day14

import java.io.File


object Day14b {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day14/example14.txt", 40) )
        println(solve("day14/input14.txt", 40) )
    }

    fun solve(filename: String, steps: Int): Int {
        var config = File(javaClass.classLoader.getResource(filename).file)
            .readText().split("\n\n", "\r\n\r\n")
        val polymer = config[0]
        val changes = config[1].split("\n", "\r\n").map{ it.split( " -> ").let{ Pair(it[0][0],it[0][1]) to it[1].first() } }.toMap()

        val totals = process( polymer, changes, steps)

        return totals.values.maxOf { it } - totals.values.minOf { it }
    }

    fun String.toPairs() = this.toList().windowed( 2, 1, false).map{ Pair( it[0], it[1] ) }

    private fun process( polymer: String, changes: Map<Pair<Char,Char>,Char>, steps: Int): Map<Char,Int> =
        if( steps > 0) polymer.toPairs()
            .fold( mutableMapOf<Char,Int>() ) { acc, molecule ->
                accumulateTotals( acc, process( "%c%c%c".format( molecule.first, changes[molecule]!!, molecule.second), changes, steps-1)) }
                .also { if( steps%10 == 0 && steps >= 20) println( steps) }
        else polymer.substring(1).toList().groupingBy { it }.eachCount()


    private fun accumulateTotals( total1: MutableMap<Char, Int>, total2: Map<Char, Int>): MutableMap<Char, Int> {
        total2.entries.map { total1[it.key] = total1.getOrDefault( it.key, 0 ) + it.value  }
        return total1
    }
}


