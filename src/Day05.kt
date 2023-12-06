

fun main() {
    fun part1(input: List<String>) :Long {
        val seeds = input.first().split(":")[1].trim().split(" ").map{it.toLong()}
        var mapContent = input.drop(1)
        val rangesList = mutableListOf<List<RangeMap>>()
        for (i in 0..6){
            val l = mapContent.drop(2).takeWhile { it.isNotEmpty() && it.first().isDigit() }
            mapContent = mapContent.drop(l.size+2)
            val ranges = l.map{
                it.split(" ").run {
                    val destRangeStart = this[0].toLong()
                    val srcRangeStart = this[1].toLong()
                    val rangeLength = this[2].toLong()
                    RangeMap(destRangeStart, srcRangeStart, rangeLength)
                }
            }
            rangesList.add(ranges)
        }
        val values = mutableListOf<Long>()
        for (seed in seeds) {
            var value = seed
            for (ranges in rangesList) {
                for (range in ranges) {
                    if (value in range.srcStart..<range.srcStart+range.len) {
                        value = value - range.srcStart + range.destStart
                        break
                    }
                }
            }
            values.add(value)
        }
        return values.min()
    }

    fun part2(input: List<String>): Long {
        val numbers = input.first().split(":")[1].trim().split(" ").map{it.toLong()}
        val seeds = mutableListOf<SeedRange>()
        for (i in (0..<numbers.size/2)) {
            val seed = numbers.drop(i*2).take(2).run {
                SeedRange(this[0], this[1])
            }
            seeds.add(seed)
        }
        var mapContent = input.drop(1)
        val rangesList = mutableListOf<List<RangeMap>>()
        for (i in 0..6){
            val l = mapContent.drop(2).takeWhile { it.isNotEmpty() && it.first().isDigit() }
            mapContent = mapContent.drop(l.size+2)
            val ranges = l.map{
                it.split(" ").run {
                    val destRangeStart = this[0].toLong()
                    val srcRangeStart = this[1].toLong()
                    val rangeLength = this[2].toLong()
                    RangeMap(destRangeStart, srcRangeStart, rangeLength)
                }
            }
            rangesList.add(ranges)
        }
        var min = -1L
        for (seedRange in seeds) {
            for (seed in seedRange.start..<seedRange.start+seedRange.len) {
                var value = seed
                for (ranges in rangesList) {
                    for (range in ranges) {
                        if (value in range.srcStart..<range.srcStart + range.len) {
                            value = value - range.srcStart + range.destStart
                            break
                        }
                    }
                }
                if (min > value || min == -1L) {
                    min = value
                }
            }
        }
        return min
    }

    val testInput = readInput("Day05_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))

}

data class RangeMap(val destStart: Long, val srcStart: Long, val len: Long)
data class SeedRange(val start: Long, val len: Long)