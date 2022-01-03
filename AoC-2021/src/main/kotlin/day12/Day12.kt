package day12

import java.io.File
import java.util.zip.ZipEntry

object Day12 {

    @JvmStatic
    fun main(args: Array<String>) {
        println(solve("day12/example12.txt"))
        println(solve("day12/example12b.txt"))
        println(solve("day12/example12c.txt"))
        println(solve("day12/input12.txt"))
    }

    fun solve(filename: String): Int {
        var map0 = File(javaClass.classLoader.getResource(filename).file)
            .readLines()
            .map { it.split( '-') }
        val map = map0.map{ Pair( it[0],it[1])}
        //println( map)

        var paths = calculatePaths( "start", map)
        //paths.forEach { path -> println( path) }

        return paths.size
    }

    val rxLowercase = Regex( "[a-z]+")
    fun String.isLowercase() = rxLowercase.matches( this)

    private fun calculatePaths( cavern: String, map: List<Pair<String,String>>, path: Path = listOf()): List<Path> {
        if( cavern == "end") return listOf(path)
        //println( getSmallCaverns( path))
        if( cavern.isLowercase() && (path.count { cavern == it.second } > if( getSmallCaverns( path) > 1) 1 else 2) )
            return listOf()
        return map.
        mapNotNull { if( it.first == cavern) it.second else if( it.second == cavern) it.first else null }.
        filter{ it != "start"}.
        flatMap { targetCavern ->
            calculatePaths( targetCavern, map, path + Pair(cavern,targetCavern)).filterNotNull()
        } ?: listOf()
    }

    private fun getSmallCaverns( path: List<Pair<String, String>>) =
        path.count { it.second.isLowercase() }


}



