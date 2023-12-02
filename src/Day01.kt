fun main() {

    fun part1CalibrationValue(line: String): Int {
        val firstDigit = line.first { it.isDigit() }.toString()
        val lastDigit = line.last { it.isDigit() }.toString()
        return firstDigit.plus(lastDigit).toInt()
    }

    fun part1(input: List<String>): Int {
        return input.toTypedArray().fold(0) { acc, s -> acc + part1CalibrationValue(s) }
    }

    fun part2CalibrationValue(line: String): Int {
        val words = listOf<String>("one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val digits = listOf<String>("1", "2", "3", "4", "5", "6", "7", "8", "9")
        val wordsToDigits =  words.zip(digits).toMap()
        // https://www.regular-expressions.info/lookaround.html
        // need positive lookahead to account for overlapping matches, where we always want the
        // last value to "win"
        val pattern = "(?=(one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9))".toRegex()

        val matchStrings = pattern.findAll(line).map { it.groupValues[1].toString() }
        val components = listOf<String>(matchStrings.first(), matchStrings.last()).map {
            if (words.contains(it)) wordsToDigits[it] else it
        }
        return components.joinToString("").toInt()
    }

    fun part2(input: List<String>): Int {
        return input.toTypedArray().fold(0) { acc, s -> acc + part2CalibrationValue(s) }
    }

    val part1TestInput = readInput("Day01_test")
    val part2TestInput = readInput("Day01Part2_test")
    check(part1(part1TestInput) == 142)

    check(part2(part2TestInput) == 281)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
