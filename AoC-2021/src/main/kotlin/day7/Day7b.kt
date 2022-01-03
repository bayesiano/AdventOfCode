package day7

import java.io.File
import kotlin.math.abs


object Day7b {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day7/example7.txt"))
        println( solve("day7/input7.txt"))
    }

    fun solve( filename: String): Long {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .flatMap { it.split(",").map { it.toInt() } }.sorted()
        //println(data)

        val pos = data[data.size/2]
        //println( pos)
        val currCost = totalCost( pos, data)
        val costDec = search( pos, currCost, -1, data)
        if( costDec < currCost ) return costDec
        val costInc = search( pos, currCost, 1, data)
        if( costInc < currCost ) return costInc
        return currCost
    }

    private fun search( pos: Int, cost: Long, delta: Int, data: List<Int> ): Long {
        val newCost = totalCost( pos + delta, data) //data.sumOf { Math.abs( it - pos ) }
        if( newCost < cost) return search( pos + delta, newCost, delta, data) //data.sumOf { Math.abs( it - pos ) }
        return cost
    }

    private fun totalCost( pos: Int, data: List<Int> ): Long {
        return data.sumOf { cost1( abs( it - pos ).toLong()) }
    }

    private fun cost1( n: Long ) : Long =
        if( n == 0L ) 0L else n + cost1( n - 1 )
}