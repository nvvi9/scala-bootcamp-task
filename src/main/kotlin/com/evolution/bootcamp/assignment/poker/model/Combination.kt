package com.evolution.bootcamp.assignment.poker.model

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.cards.Rank

sealed class Combination(val cards: List<Card>, private val combinationStrength: Int) : Comparable<Combination> {

//    abstract fun isCombination(cards: List<Card>): Boolean

    override fun compareTo(other: Combination): Int = when {
        combinationStrength == other.combinationStrength -> 0
        combinationStrength < other.combinationStrength -> -1
        combinationStrength > other.combinationStrength -> 1
        else -> throw IllegalStateException()
    }

    companion object : Parser<List<Card>, Combination> {

        override fun from(value: List<Card>): Combination = value.takeIf { it.size == 5 }?.let {
            StraightFlush.from(it)
                ?: FourOfAKind.from(it)
                ?: FullHouse.from(it)
                ?: Flush.from(it)
                ?: Straight.from(it)
                ?: ThreeOfAKind.from(it)
                ?: TwoPairs.from(it)
                ?: Pair.from(it)
                ?: HighCard.from(it)
        } ?: throw IllegalStateException()
    }
}

class StraightFlush private constructor(cards: List<Card>) : Combination(cards, 8) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>) = value.takeIf { it.isCombination() }?.let {
            StraightFlush(it)
        }

        private fun List<Card>.isCombination() =
            map { it.suit }.toSet().size == 1
                    && map { it.rank }.sorted().windowed(2, step = 1).all { it[1] - it[0] == 1 }
    }
}

class FourOfAKind private constructor(cards: List<Card>) : Combination(cards, 7) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>) = value.takeIf { it.isCombination() }?.let {
            FourOfAKind(it)
        }

        private fun List<Card>.isCombination() = map { it.rank }.sorted().let {
            it.drop(1).toSet().size == 1 || it.dropLast(1).toSet().size == 1
        }
    }
}

class FullHouse private constructor(cards: List<Card>) : Combination(cards, 6) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            FullHouse(it)
        }

        private fun List<Card>.isCombination(): Boolean {
            val sortedRanks = map { it.rank }.sorted()
            val threeRanks = sortedRanks.take(3).takeIf { it.toSet().size == 1 }
                ?: sortedRanks.takeLast(3).takeIf { it.toSet().size == 1 }
                ?: return false

            return (sortedRanks - threeRanks).toSet().size == 1
        }
    }
}

class Flush private constructor(cards: List<Card>) : Combination(cards, 5) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            Flush(it)
        }

        private fun List<Card>.isCombination(): Boolean = map { it.suit }.toSet().size == 1
    }
}

class Straight private constructor(cards: List<Card>) : Combination(cards, 4) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            Straight(it)
        }

        private fun List<Card>.isCombination(): Boolean =
            map { it.rank }.sorted().windowed(2, 1).all { it[1] - it[0] == 1 }
    }
}

class ThreeOfAKind private constructor(cards: List<Card>) : Combination(cards, 3) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            ThreeOfAKind(it)
        }

        private fun List<Card>.isCombination(): Boolean {
            val sortedCards = sortedBy { it.rank }
            val threeCards = sortedCards.take(3).takeIf { it.isTriplet() }
                ?: sortedCards.takeLast(3).takeIf { it.isTriplet() }
                ?: sortedCards.drop(1).dropLast(1).takeIf { it.isTriplet() }
                ?: return false

            val twoCards = sortedCards - threeCards

            return twoCards[0].rank != twoCards[1].rank
        }

        private fun List<Card>.isTriplet() = size == 3 && map { it.rank }.toSet().size == 1
    }
}

class TwoPairs private constructor(cards: List<Card>) : Combination(cards, 2) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            TwoPairs(it)
        }

        private fun List<Card>.isCombination(): Boolean =
            map { it.rank }.sorted().windowed(2, 1).filter { it.isDouble() }.size >= 2

        private fun List<Rank>.isDouble() = size == 2 && toSet().size == 1
    }
}

class Pair private constructor(cards: List<Card>) : Combination(cards, 1) {

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            Pair(it)
        }

        private fun List<Card>.isCombination(): Boolean =
            map { it.rank }.sorted().windowed(2, 1).any { it.isDouble() }

        private fun List<Rank>.isDouble() = size == 2 && toSet().size == 1
    }
}

class HighCard private constructor(cards: List<Card>) : Combination(cards, 0) {
    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination = HighCard(value)
    }
}