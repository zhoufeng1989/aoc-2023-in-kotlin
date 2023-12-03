import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val (digits, symbols) = findDigitsAndSymbols(input)
        return digits.sumOf { digit ->
            if (symbols.any { symbol -> isAdjacent(digit, symbol) }) digit.value else 0
        }
    }

    fun part2(input: List<String>): Int {
        val (digits, symbols) = findDigitsAndSymbols(input)
        return symbols.map { symbol ->
            Pair(symbol, digits.filter { isAdjacent(it, symbol) })
        }.sumOf { (symbol, digits) ->
            if (symbol.symbol == '*' && digits.size == 2) digits.first().value * digits.last().value else 0
        }
    }

    val testInput = readInput("Day03_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))

}

data class DigitPattern(val row: Int, val range: IntRange, val value: Int)
data class SymbolPattern(val row: Int, val index: Int, val symbol: Char)

fun findDigitsAndSymbols(input: List<String>):Pair<List<DigitPattern>, List<SymbolPattern>> {
    val regex = Regex("[0-9]+")
    val matches = input.withIndex().map{
            item ->
        val value = item.value
        val digitsMatches = regex.findAll(value)
        val symbols = value.withIndex().filter{
            !it.value.isDigit() && it.value != '.'
        }
        Triple(item.index, digitsMatches, symbols)
    }
    val digits = matches.map{
            item -> item.second.map{
            match -> DigitPattern(item.first, match.range, match.value.toInt())
    }
    }.flatMap { it }

    val symbols = matches.map{
            item -> item.third.map{
        SymbolPattern(item.first, it.index, it.value)
    }
    }.flatten()

    return Pair(digits, symbols)
}

fun isAdjacent(digit: DigitPattern, symbol: SymbolPattern) :Boolean =
    abs(digit.row-symbol.row) <= 1 && symbol.index in digit.range.first-1 .. digit.range.last+1
