package advent23.d1.trebuchet

import java.nio.file.Path
import kotlin.io.path.bufferedReader

val NUMBERS_SPELLED = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")

fun main() {
    var total = 0
    var result: Int
    var lineNo = 0
    Path.of("src/main/kotlin/advent23/d1/trebuchet/in.txt")
        .bufferedReader()
        .forEachLine { line ->
            result = twoDigitNumberFromFirstLastDigitOrSpelled(line)
            total += result
            println("${++lineNo}: $result")
        }
    println("Total is: $total")
}

fun twoDigitNumberFromFirstLastDigitOrSpelled(line: String): Int {
    if (line.isEmpty()) {
        return 0
    }
    if (line.length == 1) {
        return if (line[0].isDigit()) line[0].digitToInt() * 10 + line[0].digitToInt()
               else 0
    }

    var result = 0

    var i = 0
    var ch = line[i]
    var bufferFront = ""
    while (!ch.isDigit()) {
        bufferFront += ch
        if (i == line.length - 1) break
        ch = line[++i]
    }
    val (minIdx, numStart) = getMinIndexOfSpelledNumbers(bufferFront)
    result += if (minIdx > -1) 10 * numStart
              else 10 * line[i].digitToInt()

    i = line.length - 1
    ch = line[i]
    var bufferEnd = ""
    while (!ch.isDigit()) {
        bufferEnd += ch
        if (i == 0) break
        ch = line[--i]
    }
    bufferEnd = bufferEnd.reversed()
    val (maxIdx, numEnd) = getMaxIndexOfSpelledNumbers(bufferEnd)
    result += if (maxIdx > -1) numEnd
              else line[i].digitToInt()

    return result
}

fun getMinIndexOfSpelledNumbers(bufferFront: String): Pair<Int, Int> {
    var resultIdx = -1
    var resultNum = 0
    var min = Int.MAX_VALUE
    for (numSpellIdx in NUMBERS_SPELLED.indices) {
        val idx = bufferFront.indexOf(NUMBERS_SPELLED[numSpellIdx])
        if (idx > -1 && idx < min) {
            resultIdx = idx
            resultNum = numSpellIdx + 1
            min = idx
        }
    }
    return Pair(resultIdx, resultNum)
}

fun getMaxIndexOfSpelledNumbers(bufferFront: String): Pair<Int, Int> {
    var resultIdx = -1
    var resultNum = 0
    var max = Int.MIN_VALUE
    for (numSpellIdx in NUMBERS_SPELLED.indices) {
        val idx = bufferFront.lastIndexOf(NUMBERS_SPELLED[numSpellIdx])
        if (idx > -1 && idx > max) {
            resultIdx = idx
            resultNum = numSpellIdx + 1
            max = idx
        }
    }
    return Pair(resultIdx, resultNum)
}

