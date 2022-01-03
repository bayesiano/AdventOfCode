package day20

import java.io.File
import kotlin.math.max
import kotlin.math.min


object Problem20b {
    @JvmStatic
    fun main(args: Array<String>) {
//        println(solve( "day20/example.txt", 2))
      //  println(solve( "day20/input.txt",2 ))
        //println(solve( "day20/example.txt", 50))
       println(solve( "day20/input.txt",50 ))
        // 1 - 5106
        // 2 - 5275
    }

    fun solve( filename: String, steps: Int): Int {
        val data = File(javaClass.classLoader.getResource(filename).file)
            .readText()
            .split( "\n\n", "\r\n\r\n")

        val config = data[0]
        val image = data[1]
            .split( "\n", "\r\n")
            .flatMapIndexed{ row, l -> l.toList().mapIndexedNotNull { col, c -> if( c == '#') Pair(row, col) else null } }

        //printImage( image)
        //println( image.size)
        //println()
        var imageOut = image.map { it }
        (1..steps).forEach { step ->
           imageOut  = enhanceImage( imageOut, config,
               if(config[0] == '.') 0
               else if( step % 2 == 0) 1 else 0)
            println( "Step $step - ${imageOut.size} dots lighted")
        }
        //printImage( imageOut)
        //println( imageOut.size)
        //println()
        return imageOut.size
    }

    data class ImageLimits(
        var minRow: Int = Int.MAX_VALUE,
        var maxRow: Int = Int.MIN_VALUE,
        var minCol: Int = Int.MAX_VALUE,
        var maxCol: Int = Int.MIN_VALUE,
    ) {
        fun isInside( row: Int, col: Int): Boolean =
            row >= minRow && row <= maxRow && col >= minCol && col <= maxCol

        companion object {
            fun get( image: List<Pair<Int, Int>>): ImageLimits {
                val imageLimits = ImageLimits()
                image.forEach { p ->
                    imageLimits.minRow = min( imageLimits.minRow, p.first)
                    imageLimits.maxRow = max( imageLimits.maxRow, p.first)
                    imageLimits.minCol = min( imageLimits.minCol, p.second)
                    imageLimits.maxCol = max( imageLimits.maxCol, p.second)
                }
                return imageLimits
            }
        }
    }

    private fun printImage( image: List<Pair<Int, Int>>) {
        val limits = ImageLimits.get(image)

        (limits.minRow..limits.maxRow).forEach { row ->
            (limits.minCol..limits.maxCol).forEach { col ->
                if (Pair(row, col) in image) print('#')
                else print('.')
            }
            println()
        }
    }


    val kernel =
        (-1..1).flatMap{ row->
            (-1..1).map { col ->
                Pair( row, col)
            }
        }


    private fun enhanceImage( image: List<Pair<Int, Int>>, config: String, default: Int ): List<Pair<Int, Int>> {
        val limits = ImageLimits.get(image)

        val newImage = mutableListOf<Pair<Int,Int>>()
        (limits.minRow-1..limits.maxRow+1).map { row ->
            (limits.minCol-1..limits.maxCol+1).map { col ->
                val pos =
                    kernel.map { inc ->
                        if( !limits.isInside( row + inc.first, col + inc.second)) default
                        else if (image.any { it == Pair( row + inc.first, col + inc.second) }) 1
                        else 0 }
                        .joinToString("").toInt(2)
                val c = config[pos]
                if (c == '#') newImage += Pair( row, col )
            }
        }
        return newImage
    }

    private fun enhanceImage2( image: List<Pair<Int, Int>>, config: String, default: Int ): List<Pair<Int, Int>> {
        val limits = ImageLimits.get(image)

        return image.flatMap { point -> kernel.map { inc -> Pair(point.first + inc.first, point.second + inc.second) } }
            .mapNotNull { point ->
                val pos =
                    kernel.map { inc ->
                        if (!limits.isInside(point.first, point.second)) default
                        else if (image.any { it == point }) 1
                        else 0
                    }
                        .joinToString("").toInt(2)
                val c = config[pos]
                if (c == '#') point
                else null
            }
    }
}
//16482


