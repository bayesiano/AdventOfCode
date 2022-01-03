package day3

import java.io.File

object Day3b {
    data class State(var row: Int, var col: Int, var aim: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        process("day3/example3.txt")
        process("day3/input3.txt")
    }

    fun process(filename: String) {
        val data = File(javaClass.classLoader.getResource(filename).file).readLines()
        var dataMore = data
        var dataLeast = data

        var res1 =  sequence<String> {
            (0 until dataMore[0].length).map { i ->
                val numZeros = dataMore.count { it[i] == '0' }
                val moreCommon = if( numZeros > dataMore.size/2) '0' else '1'
                dataMore = dataMore.filter { it[i] == moreCommon}
                //println( dataMore)
                if( dataMore.size == 1) yield( dataMore[0])
            }
            yield( data[0])
        }.first().toInt(2)
        var res2 =  sequence<String> {
            (0 until data[0].length).map { i ->
                val numZeros = dataLeast.count { it[i] == '0' }
                val leastCommon = if( numZeros > dataLeast.size/2) '1' else '0'
                dataLeast = dataLeast.filter { it[i] == leastCommon}
                //println( dataLeast)
                if( dataLeast.size == 1) yield( dataLeast[0])
            }
            yield( dataLeast[0])
        }.first().toInt(2)

        val res2b = Math.pow( 2.0, (data[0].length).toDouble()) - res1 + 1

        println("$res1, $res2, $res2b, ${res1*res2}, ${res2 + res1}")
    }
// 749376
}