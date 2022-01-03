package day21

import java.io.File


object Problem21a {
    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day21/example.txt"))
        println(solve( "day21/input.txt"))
    }

    fun fibonacci(): Sequence<Int> {
        // fibonacci terms
        // 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946, ...
        return generateSequence(Pair(0, 1), { Pair(it.second, it.first + it.second) }).map { it.first }
    }

    fun solve(filename: String): Int {
        val players = File(javaClass.classLoader.getResource(filename).file)
            .readLines().map {
                it.split(' ').let {
                    Player(it[1].toInt(), it[4].toInt())
                }
            }

        val dice = Dice()

        while (true) {
            players.map { p ->
                p.move(dice.roll() + dice.roll() + dice.roll())
                if (p.score >= 1000) {
                    println("Player ${p.id} wins!")
                    println(" with ${p.score} points after ${dice.rolls} rolls")
                    return players.filter { it.score < 1000 }.first().score * dice.rolls
                }
            }
        }
    }

    class Player(val id: Int, var pos: Int) {
        var score = 0
        fun move(points: Int) {
            pos = (pos + points - 1) % 10 + 1
            score += pos
        }

    }

    class Dice {
        var points = 0
        var rolls = 0

        fun roll(): Int {
            if (points < 1000) points++ else points = 1
            rolls++
            return points
        }
    }
}


