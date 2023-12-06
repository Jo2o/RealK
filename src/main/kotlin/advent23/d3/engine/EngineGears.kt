package advent23.d3.engine

import java.nio.file.Path
import kotlin.io.path.bufferedReader

val FILE_PATH = "src/main/kotlin/advent23/d3/engine/in.txt"
val DOT = '.'

fun main() {
    val numbersWithIndexes = mutableListOf<List<IndexedNumber>>()
    Path.of(FILE_PATH)
        .bufferedReader()
        .forEachLine {
            numbersWithIndexes.add(loadIndexedNumbers(it))
        }

    val lineLength = firstLineLength(FILE_PATH) + 2   // adding '.' to start and end of line
    val threeLinesBuffer = mutableListOf<String>()
    threeLinesBuffer.add(".".repeat(lineLength))      // adding first line full of '.'

    val numbers = mutableListOf<Int>()
    var counter = -2
    Path.of(FILE_PATH)
        .bufferedReader()
        .forEachLine {
            threeLinesBuffer.add(".$it.")            // adding '.' to start and end of line
            counter++
            if (threeLinesBuffer.size == 3) {
                println(threeLinesBuffer[0])
                numbers.addAll(collectNumbersWithAdjacentSymbols(numbersWithIndexes[counter], threeLinesBuffer))
                threeLinesBuffer.removeFirst()
            }
        }
    threeLinesBuffer.add(".".repeat(lineLength))     // adding last line full of '.'
    counter++
    println(threeLinesBuffer[0])
    numbers.addAll(collectNumbersWithAdjacentSymbols(numbersWithIndexes[counter], threeLinesBuffer))
    println(numbers)
    println(numbers.sum())
}

fun collectNumbersWithAdjacentSymbols(indexedNumbers: List<IndexedNumber>, threeLinesBuffer: MutableList<String>): List<Int> {
    val numbers = mutableListOf<Int>()
    for (number in indexedNumbers) {
        if (findSpecialSymbolLeftRight(threeLinesBuffer[1], number)) {
            numbers.add(number.number)
            println(number.number)
            continue
        }
        if (findSpecialSymbolAboveBelow(threeLinesBuffer[0], number)) {
            numbers.add(number.number)
            println(number.number)
            continue
        }
        if (findSpecialSymbolAboveBelow(threeLinesBuffer[2], number)) {
            numbers.add(number.number)
            println(number.number)
            continue
        }
    }
    return numbers
}

fun findSpecialSymbolAboveBelow(line: String, number: IndexedNumber): Boolean {
    for (i in (number.startIdx - 1) .. (number.endIdx + 1)) {
        if (line[i] != DOT && !line[i].isDigit()) {
            return true
        }
    }
    return false
}

private fun findSpecialSymbolLeftRight(line: String, number: IndexedNumber): Boolean {
    return line[number.startIdx - 1] != DOT || line[number.endIdx + 1] != DOT
}

fun loadIndexedNumbers(line: String): List<IndexedNumber> {
    var numberLoadingActive = false
    var buffer = ""
    var startIdx = 0
    val indexedNumbers = mutableListOf<IndexedNumber>()
    for ((currentIdx, currentChar) in line.withIndex()) {
        if (!currentChar.isDigit() && !numberLoadingActive) {
            continue
        }
        if (!currentChar.isDigit() && numberLoadingActive) {
            indexedNumbers.add(
                IndexedNumber(buffer, buffer.toInt(), startIdx + 1, currentIdx))    // adding '.' to start and end of line
            buffer = ""
            numberLoadingActive = false
            continue
        }
        if (currentChar.isDigit() && numberLoadingActive) {
            buffer += currentChar
            continue
        }
        if (currentChar.isDigit() && !numberLoadingActive) {
            startIdx = currentIdx
            buffer += currentChar
            numberLoadingActive = true
            continue
        }
    }
    return indexedNumbers
}

fun firstLineLength(filePath: String): Int {
    return Path.of(filePath).bufferedReader()
        .useLines { lines -> lines.firstOrNull() }
        ?.length
        ?: -1
}







