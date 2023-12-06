package advent23.d1.trebuchet

import java.nio.file.Path
import kotlin.io.path.bufferedReader

fun main() {
    var result = 0
    println("Current working directory: ${System.getProperty("user.dir")}")
    Path.of("src/main/kotlin/advent23/d1/trebuchet/in.txt")
        .bufferedReader()
        .useLines { lines -> lines.forEach {
            result += twoDigitNumberFromFirstLastDigit(it)
        }
    }
    println("Result is: $result")
}

fun twoDigitNumberFromFirstLastDigit(line: String): Int {
    if (line.isEmpty()) {
        return 0
    }
    if (line.length == 1) {
        return if (line[0].isDigit()) line[0].digitToInt() else 0
    }

    var result = 0
    var frontStopper = false
    var endIdx = line.length -1
    var endStopper = false

    for (frontIdx in line.indices) {
        if (frontStopper && endStopper) {
            break
        }
        if (line[frontIdx].isDigit() && !frontStopper) {
            result += 10 * line[frontIdx].digitToInt()
            frontStopper = true
        }
        if (line[endIdx].isDigit() && !endStopper) {
            result += line[endIdx].digitToInt()
            endStopper = true
        }
        endIdx -= 1
    }
    return result
}