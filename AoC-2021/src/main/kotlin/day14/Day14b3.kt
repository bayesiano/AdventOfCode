package day14

import java.io.File


object Day14b3 {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day14/example14.txt", 0))
        println(solve("day14/example14.txt", 1))
        println(solve("day14/example14.txt", 2))
        println(solve("day14/example14.txt", 3))
        println(solve("day14/example14.txt", 4))
        println(solve("day14/example14.txt", 5))
        println(solve("day14/example14.txt", 10))
        println(solve("day14/example14.txt", 40))
        println(solve("day14/input14.txt", 40))
    }

    class Totals( polymer: String) {
        private val accumulated = polymer.toList()
            .groupingBy { it }.eachCount()
            .mapValues { it.value.toLong() } //mutableMapOf<Char, Long>()
            .toMutableMap()
            .withDefault { 0L }

        fun add( other: Totals) {
            other.accumulated.map { accumulated[it.key] = accumulated.getValue(it.key) + it.value }
        }

        fun getMaxOccurrence(): Long = accumulated.values.maxOf { it }
        fun getMinOccurrence(): Long = accumulated.values.minOf { it }
        override fun toString(): String {
            return "Totals($accumulated) - " + accumulated.values.sum()
        }

        //operator fun set( c: Char, value: Long ) = accumulated::set
        //fun getValue( c: Char) = accumulated.getValue( c)
        fun sum( c: Char, n: Long ) {
            accumulated[c] = accumulated.getValue(c) + n
        }
    }

    fun solve(filename: String, steps: Int): Long {
        var config = File(javaClass.classLoader.getResource(filename).file)
            .readText().split("\n\n", "\r\n\r\n")
        val polymer = config[0]
        val changes = config[1].split("\n", "\r\n").map { it.split(" -> ").let { it[0] to it[1].first() } }.toMap()
        val cache = Array(steps+1) { mutableMapOf<String, Totals>()}

        val totals = process(polymer, changes, steps)
        println( totals)
        return totals.getMaxOccurrence() - totals.getMinOccurrence()
    }

    private fun process( polymer: String, changes: Map<String, Char>, steps: Int ): Totals {
        val cache = Array(steps+1) { mutableMapOf<String, Totals>()}
        return process(polymer, changes, steps, cache).also { it.sum( polymer.last(), 1L) }
    }

    fun String.toPairs() = this.toList().windowed(2, 1, false).map { Pair(it[0], it[1]) }

    private fun process(
        polymer: String,
        changes: Map<String, Char>,
        step: Int,
        cache: Array<MutableMap<String, Totals>>
    ): Totals {
        if( step <= 0) return Totals( polymer.substring( 0, polymer.length - 1 ) )
        val totals = Totals( "")
        if( step > 0) {
            (0 until polymer.length - 1).forEach { i ->
                val molecule = "%c%c%c".format(polymer[i], changes[polymer.substring(i, i + 2)] ?: ' ', polymer[i + 1])
                //println( "$step - $molecule")
                val subtotal = cache[step][molecule] ?: process(molecule, changes, step - 1, cache).also {
                    cache[step][molecule] = it
                }
                totals.add(subtotal)
            }
            //totals.sum( polymer.last(), 1L)
        }
        return totals
    }

}


