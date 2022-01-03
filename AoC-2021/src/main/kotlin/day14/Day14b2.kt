package day14

import java.io.File


object Day14b2 {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day14/example14.txt", 5))
        println(solve("day14/example14.txt", 10))
        println(solve("day14/example14.txt", 40))
        //println(solve("input14.txt", 2))
    }

    class Totals( polymer: String) {
        val accumulated = polymer.toList().groupingBy { it }.eachCount().toMutableMap() //mutableMapOf<Char, Long>()

        fun add(v: Totals) {
            v.accumulated.map { accumulated[it.key] = accumulated.getOrDefault(it.key, 0) + it.value }
        }

        fun getMaxOccurrence(): Int = accumulated.values.maxOf { it }
        fun getMinOccurrence(): Int = accumulated.values.minOf { it }
        override fun toString(): String {
            return "Totals($accumulated) - " + accumulated.values.sum()
        }
    }

    fun solve(filename: String, steps: Int): Int {
        var config = File(javaClass.classLoader.getResource(filename).file)
            .readText().split("\n\n", "\r\n\r\n")
        val polymer = config[0]
        val changes = config[1].split("\n", "\r\n").map { it.split(" -> ").let { it[0] to it[1].first() } }.toMap()
        val cache = Array(steps+1) { mutableMapOf<String, Totals>()}

        val totals = process(polymer, changes, steps, cache)
        println( totals)
        return totals.getMaxOccurrence() - totals.getMinOccurrence()
    }

    fun String.toPairs() = this.toList().windowed(2, 1, false).map { Pair(it[0], it[1]) }

    private fun process(
        polymer: String,
        changes: Map<String, Char>,
        step: Int,
        cache: Array<MutableMap<String, Totals>>
    ): Totals {
        if( step <= 0) {
            return Totals(polymer)
        }
        val newPolimer = StringBuilder()
        val totals = Totals( "")
        (0 until polymer.length-1).forEach { i ->
            newPolimer.append(polymer[i])
            newPolimer.append(changes[polymer.substring(i, i + 2)] ?: ' ')
            cache[step][newPolimer.toString()] ?:
                process( newPolimer.toString(), changes, step -1, cache).also{
                    cache[step][newPolimer.toString()] = it
                }
            totals.add( cache[step][newPolimer.toString()]!!)
        }
        newPolimer.append( polymer.last())
        return cache[step][newPolimer.toString()] ?: process( newPolimer.toString(), changes, step -1, cache).also{ cache[step][newPolimer.toString()] = it}
    }

}


