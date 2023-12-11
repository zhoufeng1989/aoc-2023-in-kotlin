fun main(){
    fun part1(input: List<String>) : Int {
        return input.sumOf { line ->
            val seq = line.split(" ").map{it.toInt()}
            var currentSeq = seq
            val sequences = mutableListOf(seq)
            while (true) {
                val diffSeq = generateDiffSeq(currentSeq)
                if (diffSeq.all { it == 0 }) {
                    break
                }
                sequences.add(diffSeq)
                currentSeq = diffSeq
            }
            val diff = sequences.removeAt(sequences.size-1)[0]
            sequences.foldRight(diff) {
                    s, acc -> s.last() + acc
            }
        }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val seq = line.split(" ").map{it.toInt()}
            var currentSeq = seq
            val sequences = mutableListOf(seq)
            while (true) {
                val diffSeq = generateDiffSeq(currentSeq)
                if (diffSeq.all { it == 0 }) {
                    break
                }
                sequences.add(diffSeq)
                currentSeq = diffSeq
            }
            val diff = sequences.removeAt(sequences.size-1)[0]
            sequences.foldRight(diff) {
                    s, acc -> s.first() -acc
            }
        }
    }


    val testInput = readInput("Day09_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

fun generateDiffSeq(seq: List<Int>): List<Int> {
    val diffSeq = mutableListOf<Int>()
    for (i in 0..<seq.size-1) {
        val item = seq[i+1] - seq[i]
        diffSeq.add(item)
    }
    return diffSeq
}