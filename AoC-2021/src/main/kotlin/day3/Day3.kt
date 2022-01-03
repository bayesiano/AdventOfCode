import java.io.File

object Day3 {
    val filename = listOf<String>( "day3/example3.txt", "day3/input3.txt")

    val data =
        //sequence<Map<String,Pair<Int,Int>>> {
        filename.map {
            File(javaClass.classLoader.getResource(it).file).readLines()
        }

    data class State( var row: Int, var col:Int, var aim: Int)

    @JvmStatic
    fun main(args: Array<String>) {
        val pos = data.map{
            val stats = Array( it[0].length) { Array(2) { 0} }
            it.forEach { line ->
                line.forEachIndexed { index, c ->  stats[index][if(c == '0') 0 else 1] ++}
            }
            val gamma = stats.map { if( it[0] > it[1]) 0 else 1 }.joinToString("").toInt(2)
            val eps = stats.map { if( it[0] > it[1]) 1 else 0 }.joinToString("").toInt(2)
//            val eps = Math.pow( 2.0, (stats.size+1).toDouble()) - gamma
            Pair( gamma, eps.toInt())
        }
        pos.forEach { println( "$it, ${it.first * it.second}")}
    }
}