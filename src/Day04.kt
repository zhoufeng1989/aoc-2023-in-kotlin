import kotlin.math.pow


fun main() {
    fun part1(input: List<String>) =
        input.sumOf { line ->
            line.split(":")[1].trim().split("|").run {
                val winningNumbers = this.first().trim().split(" ").mapNotNull {
                    it.toIntOrNull()
                }.toSet()
                val matchedCount = this.last().trim().split(" ").mapNotNull {
                    it.toIntOrNull()
                }.filter {
                    it in winningNumbers
                }.size
                if (matchedCount > 0) 2.0.pow(matchedCount - 1).toInt() else 0
            }
        }


    fun part2(input: List<String>): Int {
        val cards = mutableMapOf<Int, Int>()
        input.withIndex().map {lineWithIndex ->
            val index = lineWithIndex.index
            val line = lineWithIndex.value
            val count = line.split(":")[1].trim().split("|").run {
                val winningNumbers = this.first().trim().split(" ").mapNotNull {
                    it.toIntOrNull()
                }.toSet()
                this.last().trim().split(" ").mapNotNull {
                    it.toIntOrNull()
                }.filter {
                    it in winningNumbers
                }.size
            }
            cards[index] = cards.getOrDefault(index, 1)
            for (i in IntRange(1, count)) {
                cards[index+i] = cards.getOrDefault(index+i, 1) + cards[index]!!
            }
        }
        return cards.map{
            it.value
        }.sum()
    }

    val testInput = readInput("Day04_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))

}