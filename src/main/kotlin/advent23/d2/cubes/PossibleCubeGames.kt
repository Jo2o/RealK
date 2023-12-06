package advent23.d2.cubes

import java.nio.file.Path
import kotlin.io.path.bufferedReader

val RED = "red"
val GREEN = "green"
val BLUE = "blue"

val BALL_COUNTS = hashMapOf(RED to 12, GREEN to 13, BLUE to 14)

fun main() {
    var total = 0
    Path.of("src/main/kotlin/advent23/d2/cubes/in.txt")
        .bufferedReader()
        .forEachLine { line -> total += calculateGamesPower(line)
        }
    println("Total is: $total")
}


fun calculateGamesPower(line: String): Int {
    val games = line.substringAfter(": ").split(';')
    var maxBallCounts = createZeroBallCountMap()
    for (game in games) {
        maxBallCounts = calculateMaxBallCounts(maxBallCounts, gameToMap(game))
    }
    var power = 1
    for (value in maxBallCounts.values) {
        power *= value
    }
    return power
}

fun isPossible(maxBallCounts: HashMap<String, Int>): Boolean {
    for ((color, count) in BALL_COUNTS) {
        val maxCount = maxBallCounts[color] ?: 0
        if (maxCount > count) {
            return false
        }
    }
    return true
}

fun gameToMap(gameStr: String): Map<String, Int>{
    val result = createZeroBallCountMap()
    val ballCountAndColors = gameStr.split(',')
    for (ballCountAndColor in ballCountAndColors) {
        when {
            ballCountAndColor.contains(RED) ->
                result[RED] = ballCountAndColor.substringBeforeLast(' ').trim().toInt()
            ballCountAndColor.contains(GREEN) ->
                result[GREEN] = ballCountAndColor.substringBeforeLast(' ').trim().toInt()
            ballCountAndColor.contains(BLUE) ->
                result[BLUE] = ballCountAndColor.substringBeforeLast(' ').trim().toInt()
        }
    }
    return result
}

fun calculateMaxBallCounts(maxBallCounts: HashMap<String, Int>, gameToMap: Map<String, Int>): HashMap<String, Int> {
    for ((color, count) in maxBallCounts) {
        val gameCount = gameToMap[color] ?: 0
        if (gameCount > count) {
            maxBallCounts[color] = gameCount
        }
    }
    return maxBallCounts
}

fun createZeroBallCountMap(): HashMap<String, Int> {
    return hashMapOf(RED to 0, GREEN to 0, BLUE to 0)
}
