package day10

import java.io.File


object Day10b {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day10/example10.txt"))
        println(solve("day10/input10.txt"))
    }

    val score = mapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4,
    )

    fun solve(filename: String): Long {
        var lines = File(javaClass.classLoader.getResource(filename).file)
            .readLines()

        val missingChars = lines.mapNotNull { validate(it) }
        //println( missingChars)

        val scores = missingChars.map { calculateScore( it) }
        //println( scores.sorted())
        return scores.sorted()[scores.size/2]
    }

    private fun calculateScore( str: String, acc: Long = 0): Long =
        if( str.isEmpty()) acc
        else calculateScore( str.dropLast(1), score[str.last()]!! + 5 * acc)


    val mates = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    private fun validate(line: String, acc: String = ""): String? =
        if (line.isEmpty()) acc
        else if (line[0] in mates.keys) validate(line.substring(1), acc + line[0])
        else if (line[0] == mates[acc.last()]) validate(line.substring(1), acc.dropLast(1))
        else null // line[0] // error
}


