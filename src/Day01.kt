fun main() {
    fun part1(input: List<String>): Int {
        val digits = input.map{
                s -> s.filter{
                c -> c in '0'..'9'
                }.map {
                    d -> d.digitToInt()
                }
        }
        return digits.sumOf {
            it.first() * 10 + it.last()
        }
    }

    fun part2(input: List<String>): Int =
        input.sumOf {
            val digits = findDigitsInString(it)
            digits.first() * 10 + digits.last()
        }

    val testInput1 = readInput("Day01_test")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    check(part1(input) == 54601)
    check(part2(input) == 54078)
}

data class Digit(val pattern:String, val value: Int)
fun findDigitsInString(str: String): List<Int> {
    val digitsTable = listOf(
        Digit("one", 1),
        Digit("two",2),
        Digit("three", 3),
        Digit("four",4),
        Digit("five", 5),
        Digit("six", 6),
        Digit("seven", 7),
        Digit("eight", 8),
        Digit("nine",9),
        Digit("0", 0),
        Digit("1", 1),
        Digit("2", 2),
        Digit("3", 3),
        Digit("4", 4),
        Digit("5", 5),
        Digit("6", 6),
        Digit("7", 7),
        Digit("8",8),
        Digit("9", 9)
    )
    val digits = mutableListOf<Int>()
    for (index in 0..str.length) {
        val subStr = str.substring(index)
        val digit = digitsTable.find{subStr.startsWith(it.pattern, false)}
        if (digit != null) {
            digits.add(digit.value)
        }
    }
    return digits
}