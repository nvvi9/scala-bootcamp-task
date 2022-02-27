package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class ThreeOfAKind private constructor(cards: List<Card>) : Combination(cards, 3) {

    private val triplet: List<Card> = cards.sortedBy { it.rank }.run {
        take(3).takeIf { it.isTriplet() }
            ?: takeLast(3).takeIf { it.isTriplet() }
            ?: drop(1).dropLast(1).takeIf { it.isTriplet() }
    } ?: throw IllegalStateException()

    private val kickers = cards - triplet

    override fun compareWith(other: Combination): Int? = (other as? ThreeOfAKind)?.let { threeOfAKindCombination ->
        val tripletRank = triplet.first().rank
        val otherTripletRank = threeOfAKindCombination.triplet.first().rank

        when {
            tripletRank > otherTripletRank -> 1
            tripletRank < otherTripletRank -> -1
            tripletRank == otherTripletRank -> {
                val maxKickerRank = kickers.maxByOrNull { it.rank }?.rank ?: throw IllegalStateException()
                val otherMaxKickerRank =
                    threeOfAKindCombination.kickers.maxByOrNull { it.rank }?.rank ?: throw IllegalStateException()

                when {
                    maxKickerRank > otherMaxKickerRank -> 1
                    maxKickerRank < otherMaxKickerRank -> -1
                    maxKickerRank == otherMaxKickerRank -> {
                        val minKickerRank = kickers.minByOrNull { it.rank }?.rank ?: throw IllegalStateException()
                        val otherMinKickerRank = threeOfAKindCombination.kickers.minByOrNull { it.rank }?.rank
                            ?: throw  IllegalStateException()

                        when {
                            minKickerRank > otherMinKickerRank -> 1
                            minKickerRank < otherMinKickerRank -> -1
                            minKickerRank == otherMinKickerRank -> 0
                            else -> throw IllegalStateException()
                        }
                    }
                    else -> throw IllegalStateException()
                }
            }
            else -> throw IllegalStateException()
        }
    }

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