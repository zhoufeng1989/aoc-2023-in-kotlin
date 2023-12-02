fun main() {
    fun part1(input: List<String>): Int =
        input.map {
            parseGame(it)
        }.filter { game ->
            game.cubes.all { cube ->
                cube.red <= 12 && cube.green <= 13 && cube.blue <= 14
            }
        }.sumOf { it.id }


    fun part2(input: List<String>): Int =
        input.map {
            parseGame(it)
        }.sumOf { game ->
            game.cubes.maxOf { it.red } * game.cubes.maxOf { it.blue } * game.cubes.maxOf { it.green }
        }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    check(part1(input) == 3099)
    check(part2(input) == 72970)
}

data class Cubes(val red: Int, val blue: Int, val green: Int)
data class Game(val id: Int, val cubes: List<Cubes>)

fun parseGame(str: String) :Game {
    val (gameStr, cubesStr) = str.split(":")
    val id = gameStr.trim().split(" ")[1].toInt()
    val cubesList = cubesStr.split(";").map {
        round ->
            var (red, blue, green) = listOf(0, 0, 0)
            round.split(",").map { cubeStr ->
                    val (countStr, color) = cubeStr.trim().split(" ")
                    val cubeCount = countStr.trim().toInt()
                    when (color.trim()) {
                        "red" -> red = cubeCount
                        "blue" -> blue = cubeCount
                        "green" -> green = cubeCount
                    }
            }
            Cubes(red, blue, green)
    }
    return Game(id, cubesList)
}