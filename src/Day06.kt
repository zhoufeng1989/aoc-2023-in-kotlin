

fun main() {
    fun part1(input: List<String>) :Int {
        val durations = input[0].split(":")[1].split(" ").mapNotNull{it.trim().toIntOrNull()}
        val distances = input[1].split(":")[1].split(" ").mapNotNull{it.trim().toIntOrNull()}
        val winCounts = mutableListOf<Int>()
        for (i in durations.indices) {
            val duration = durations[i]
            val distance = distances[i]
            val loseDurations1 = (1..<duration).takeWhile { duration * it - it * it - distance <= 0}
            val loseDurations2 = (1..<duration).reversed().takeWhile { duration * it - it * it - distance <= 0}
            val winCount = duration - loseDurations1.size - loseDurations2.size -1
            if (winCount > 0) {
                winCounts.add(winCount)
            }
        }
        return winCounts.reduce{
            acc, item -> acc * item
        }
    }

    fun part2(input: List<String>): Long {
        val duration = input[0].split(":")[1].filter { it.isDigit() }.toLong()
        val distance = input[1].split(":")[1].filter { it.isDigit() }.toLong()
        val loseDurations = (1..<duration).count { duration * it - it * it - distance <= 0}
        val winCount = duration - loseDurations - 1
        return if (winCount > 0) winCount else 0
    }

    val testInput = readInput("Day06_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

