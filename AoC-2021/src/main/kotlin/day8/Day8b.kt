package day8

import java.io.File


object Day8b {
    @JvmStatic
    fun main(args: Array<String>) {
        println( solve("day8/example8.txt"))
        println( solve("day8/example8b.txt"))
        println( solve("day8/input8.txt"))
    }

    private fun String.order()= toList().sorted().joinToString("")

    fun solve( filename: String): Int {
        var data = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.split(" | ") }
        //println(data)

        val combinations = data.map { it.map {it.split( ' ').map { it.order() } } }
        val conversion = combinations.map { l ->
            val translation = mutableMapOf<String,Int>()
            val one = l[0].find { it.length == 2 }?.also{ translation[it] = 1 }
            val seven = l[0].find { it.length == 3 }?.also{ translation[it] = 7}
            val four = l[0].find { it.length == 4 }?.also{ translation[it] = 4}
            l[0].find { it.length == 7 }?.let{ translation[it] = 8}

            val six = l[0].find { s -> s.length == 6 && !(seven?.all{ s.contains( it)} ?: false) }?.also{ translation[it] = 6}
            val nine = l[0].find { s -> s.length == 6 && four?.all{ s.contains( it)} ?: false }?.also{ translation[it] = 9}
            val zero = l[0].find { s -> s.length == 6 && s != nine && s != six }?.also{ translation[it] = 0}

            val three = l[0].find { s -> s.length == 5 && seven?.all{ s.contains( it)} ?: false }?.also{ translation[it] = 3}
            val five = l[0].find { s -> s.length == 5 && !three.equals(s) && four?.count{ s.contains( it)} == 3 }?.also{ translation[it] = 5}
            val two = l[0].find { s -> s.length == 5 && s != five && !s.equals( three ) }?.also{ translation[it] = 2}

//            println( combinations )
            l[1].map{ translation[it]}.joinToString("").toInt()
        }

//        println( conversion )
        return conversion.sum()
    }
}