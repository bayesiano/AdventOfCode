package day10

import java.io.File

object Day10 {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day10/example10.txt"))
        println(solve("day10/input10.txt"))
    }

    val score = mapOf(
        ')' to 3,
        ']' to 57,
        '}' to 1197,
        '>' to 25137,
    )

    fun solve(filename: String): Int {
        var lines = File(javaClass.classLoader.getResource(filename).file)
            .readLines()

        val errors = lines.mapNotNull { validate(it) }
        //println(errors)

        return errors.sumOf { score[it]!! }
    }

    val mates = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>',
    )

    private fun validate(line: String, acc: String = ""): Char? =
        if (line.length == 0) null
        else if (line[0] in mates.keys) validate(line.substring(1), acc + line[0])
        else if (line[0] == mates[acc.last()]) validate(line.substring(1), acc.dropLast(1))
        else line[0]
}


