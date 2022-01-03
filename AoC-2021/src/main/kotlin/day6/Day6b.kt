package day6
import java.io.File

object Day6b {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve2("day6/example6.txt", 18))
        println( solve2("day6/example6.txt", 80))
        println( solve2("day6/input6.txt", 80))
        println( solve2("day6/input6.txt", 256))
    }

    fun solve(filename: String, numDays: Int): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .flatMap { it.split(",").map { it.toInt() } }
        //println(data)

        (1..numDays).forEach { day ->
            data += data.filter { it == 0 }.map { 9 }
            data = data.map { if( it == 0) 6 else it - 1 }
            //println( "$day ${data.size}" )
        }
        return data.size
    }
    fun solve2(filename: String, numDays: Int): Long {
        val lanternfishPopulation = mutableMapOf<Int,Long>()

        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .flatMap {
                it.split(",").map { it.toInt() }
            }
        //println(data)
        data.forEach { lanternfish ->
            lanternfishPopulation[lanternfish] = lanternfishPopulation.getOrDefault(lanternfish, 0) + 1
        }

        (1..numDays).forEach { day ->
            lanternfishPopulation[9] = lanternfishPopulation.getOrDefault(0, 0)
            val toRenew = lanternfishPopulation.getOrDefault(0, 0)
            (1..9).forEach { i ->
                lanternfishPopulation[i-1] = lanternfishPopulation.getOrDefault( i, 0)
            }
            lanternfishPopulation[6] = lanternfishPopulation.getOrDefault(6, 0) + toRenew
            lanternfishPopulation[9] = 0
            //println( "$day $lanternfishPopulation")
        }
        return lanternfishPopulation.values.sum()
    }

    private fun numGenerated( lanternfish: Int, numDays: Int): Byte {
        val numDays2 = numDays-lanternfish
        return 1
    }
}