

fun main() {
    fun part1(input: List<String>) :Int {
        val cards = input.map{ line ->
            val parts = line.split(" ")
            val cards = parts[0].trim()
            val bid = parts[1].trim().toInt()
            val type = getHandType(cards)
            Hands(cards, type, bid)
        }.sorted()
        return cards.withIndex().sumOf {
                cardWithIndex -> (cardWithIndex.index + 1) * cardWithIndex.value.bid
        }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map{ line ->
            val parts = line.split(" ")
            val cards = parts[0].trim()
            val bid = parts[1].trim().toInt()
            val type = getHandTypeWithJoker(cards)
            HandsWithJoker(cards, type, bid)
        }.sorted()
        return cards.withIndex().sumOf {
                cardWithIndex -> (cardWithIndex.index + 1) * cardWithIndex.value.bid
        }
    }

    val testInput = readInput("Day07_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

enum class HandType {
    HighCard,
    OnePair,
    TwoPair,
    ThreeOfAKind,
    FullHouse,
    FourOfAKind,
    FiveOfAKind
}

val cardsStrength =mapOf<Char,Int>(
    '2' to 1,
    '3' to 2,
    '4' to 3,
    '5' to 4,
    '6' to 5,
    '7' to 6,
    '8' to 7,
    '9' to 8,
    'T' to 9,
    'J' to 10,
    'Q' to 11,
    'K' to 12,
    'A' to 13
)


val cardsStrengthWithJoker =mapOf<Char,Int>(
    'J' to 0,
    '2' to 1,
    '3' to 2,
    '4' to 3,
    '5' to 4,
    '6' to 5,
    '7' to 6,
    '8' to 7,
    '9' to 8,
    'T' to 9,
    'Q' to 10,
    'K' to 11,
    'A' to 12
    )

fun getHandType(cards: String) :HandType {
    val m = mutableMapOf<Char, Int>()
    cards.map{
        m[it] = m.getOrDefault(it, 0) + 1
    }
    return when (m.size) {
        1 -> HandType.FiveOfAKind
        2 -> if (m.values.max() == 4) HandType.FourOfAKind else HandType.FullHouse
        3 -> if (m.values.max() == 3) HandType.ThreeOfAKind else HandType.TwoPair
        4 -> HandType.OnePair
        else -> HandType.HighCard
    }
}

fun getHandTypeWithJoker(cards: String) :HandType {
    val m = mutableMapOf<Char, Int>()
    cards.map{
        m[it] = m.getOrDefault(it, 0) + 1
    }
    val jokerCount = m.remove('J')?:0

    return when (m.size) {
        0 -> HandType.FiveOfAKind
        1 -> HandType.FiveOfAKind
        2 -> if (m.values.max() + jokerCount == 4) HandType.FourOfAKind else HandType.FullHouse
        3 -> if (m.values.max() + jokerCount == 3) HandType.ThreeOfAKind else HandType.TwoPair
        4 -> HandType.OnePair
        else -> HandType.HighCard
    }
}

data class HandsWithJoker(val cards: String, val type: HandType, val bid: Int) :Comparable<HandsWithJoker> {
    override fun compareTo(other: HandsWithJoker): Int {
        if (this.type > other.type) {
            return 1
        }
        if (this.type < other.type) {
            return -1
        }
        for (index in this.cards.indices) {
            val thisCardStrength = cardsStrengthWithJoker[this.cards[index]]!!
            val otherCardStrength = cardsStrengthWithJoker[other.cards[index]]!!
            if (thisCardStrength > otherCardStrength) {
                return 1
            } else if (thisCardStrength < otherCardStrength) {
                return -1
            }
        }
        return 0
    }
}

data class Hands(val cards: String, val type: HandType, val bid: Int) :Comparable<Hands> {
    override fun compareTo(other: Hands): Int {
        if (this.type > other.type) {
            return 1
        }
        if (this.type < other.type) {
            return -1
        }
        for (index in this.cards.indices) {
            val thisCardStrength = cardsStrength[this.cards[index]]!!
            val otherCardStrength = cardsStrength[other.cards[index]]!!
            if (thisCardStrength > otherCardStrength) {
                return 1
            } else if (thisCardStrength < otherCardStrength) {
                return -1
            }
        }
        return 0
    }
}
