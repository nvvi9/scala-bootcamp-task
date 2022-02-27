package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class FullHouse private constructor(cards: List<Card>) : Combination(cards, 6) {

    private val triplet = cards.sortedBy { it.rank }.run {
        take(3).takeIf { it.map { it.rank }.toSet().size == 1 }
            ?: takeLast(3).takeIf { it.map { it.rank }.toSet().size == 1 }
    } ?: throw IllegalStateException()

    private val pair = cards - triplet

    override fun compareWith(other: Combination): Int? = (other as? FullHouse)?.let { fullHouseCombination ->
        val tripletRank = triplet.first().rank
        val otherTripletRank = fullHouseCombination.triplet.first().rank

        when {
            tripletRank > otherTripletRank -> 1
            tripletRank < otherTripletRank -> -1
            tripletRank == otherTripletRank -> {
                val pairRank = pair.first().rank
                val otherPairRank = fullHouseCombination.pair.first().rank

                when {
                    pairRank > otherPairRank -> 1
                    pairRank < otherPairRank -> -1
                    pairRank == otherPairRank -> 0
                    else -> throw IllegalStateException()
                }
            }
            else -> throw IllegalStateException()
        }
    }

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