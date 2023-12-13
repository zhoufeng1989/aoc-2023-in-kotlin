import kotlin.math.min

fun main()  {
    fun part1(input: List<String>): Long {
        val patterns = parseInput(input)
        var sum = 0L
        for (pattern in patterns) {
            val mirrorLinePair = findMirror(pattern)
            sum += if (mirrorLinePair != null) {
                (mirrorLinePair.first+1) * 100
            } else {
                val transposePattern = transpose(pattern)
                val verticalMirrorLinePair = findMirror(transposePattern)
                (verticalMirrorLinePair?.first?.plus(1))?:0
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val patterns = parseInput(input)
        var sum = 0L
        for (pattern in patterns) {
            val mirrorLinePair = findMirrorWithSmudge(pattern)
            sum += if (mirrorLinePair != null) {
                (mirrorLinePair.first+1) * 100
            } else {
                val transposePattern = transpose(pattern)
                val verticalMirrorLinePair = findMirrorWithSmudge(transposePattern)
                (verticalMirrorLinePair?.first?.plus(1))?:0
            }
        }
        return sum
    }

    val testInput = readInput("Day13_test")
    println(part1(testInput))
    println(part2(testInput))
//
    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))

}

fun parseInput(input: List<String>): List<List<String>> {
    var lst = mutableListOf<String>()
    val result = mutableListOf<List<String>>()
    for (line in input) {
        if (line.isBlank()) {
            result.add(lst)
            lst = mutableListOf()
        } else {
            lst.add(line)
        }
    }
    if (lst.isNotEmpty()) {
        result.add(lst)
    }
    return result
}

fun transpose(lst: List<String>): List<String> {
    val row = lst[0].length
    val column = lst.size
    val array = Array(row){CharArray(column)}
    for (r in lst.indices) {
        for (c in (0..<row)) {
            array[c][r] = lst[r][c]
        }
    }
    return array.map {
        it.joinToString("")
    }.toList()
}

fun findMirror(pattern: List<String>):Pair<Int, Int>? {
    val m = getSameMap(pattern)
    for (entry in m) {
        if (entry.value && entry.key.first+1==entry.key.second) {
            val (index1, index2) = entry.key
            val count = min(index1, pattern.size - 1 - index2)
            val indices = (index1-count..<index1) zip (index2+1..index2+count).reversed()
            if (indices.all { m[Pair(it.first, it.second)]!! }) {
                return Pair(index1, index2)
            }
        }
    }
    return null
}

fun findMirrorWithSmudge(pattern: List<String>):Pair<Int, Int>? {
    val m = getSameMap(pattern)
    val smudgeMap = getSmudgeMap(pattern)
    for (entry in smudgeMap){
        val (index1, index2) = entry.key
        if (index1 + 1 != index2) {
            continue
        }
        val smudged = entry.value == 1
        val equal = m[entry.key]!!
        if (smudged || equal) {
            val count = min(index1, pattern.size - 1 - index2)
            val indices = (index1-count..<index1) zip (index2+1..index2+count).reversed()
            if (smudged) {
                if (indices.all { m[Pair(it.first, it.second)]!! }) {
                    return Pair(index1, index2)
                }
            }
            if (equal) {
                var smudged = false
                var notSatisfied = false
                for (indexPair in indices) {
                    if (smudgeMap[indexPair] == 1) {
                        if (smudged) {
                            notSatisfied = true
                            break
                        }
                        smudged = true
                    } else if (!m[indexPair]!!){
                        notSatisfied = true
                        break
                    }
                }
                if (!notSatisfied && smudged) {
                    return Pair(index1, index2)
                }
            }
        }
    }
    return null
}

fun getSameMap(pattern: List<String>):Map<Pair<Int, Int>, Boolean>{
    val m = mutableMapOf<Pair<Int, Int>, Boolean>()
    for (index1 in (0..pattern.size-2)){
        for (index2 in (index1+1..<pattern.size)){
            m[Pair(index1, index2)] = pattern[index1] == pattern[index2]
        }
    }
    return m
}

fun getSmudgeMap(pattern: List<String>): Map<Pair<Int, Int>, Int> {
    val m = mutableMapOf<Pair<Int, Int>, Int>()
    for (index1 in (0..pattern.size-2)){
        for (index2 in (index1+1..<pattern.size)){
            val row1 = pattern[index1]
            val row2 = pattern[index2]
            m[Pair(index1, index2)] = row1.zip(row2).count{it.first!=it.second}
        }
    }
    return m
}