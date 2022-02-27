package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.cards.Rank
import com.evolution.bootcamp.assignment.poker.utils.Parser

class TwoPairs private constructor(cards: List<Card>) : Combination(cards, 2) {

    private val pairs = cards.sortedBy { it.rank }.windowed(2, 1)
        .filter { it.map { it.rank }.isDouble() }.map { it[0] to it[1] }

    private val kicker = (cards - (pairs.flatMap { listOf(it.first, it.second) })).first()

    override fun compareWith(other: Combination): Int? = (other as? TwoPairs)?.let { twoPairsCombination ->
        val maxPairsRank = pairs.maxOfOrNull { it.first.rank } ?: throw IllegalStateException()
        val otherMaxPairsRank =
            twoPairsCombination.pairs.maxOfOrNull { it.first.rank } ?: throw IllegalStateException()

        when {
            maxPairsRank > otherMaxPairsRank -> 1
            maxPairsRank < otherMaxPairsRank -> -1
            maxPairsRank == otherMaxPairsRank -> {
                val minPairsRank = pairs.minOfOrNull { it.first.rank } ?: throw IllegalStateException()
                val otherMinPairsRank =
                    twoPairsCombination.pairs.minOfOrNull { it.first.rank } ?: throw IllegalStateException()

                when {
                    minPairsRank > otherMinPairsRank -> 1
                    minPairsRank < otherMinPairsRank -> -1
                    minPairsRank == otherMinPairsRank -> {
                        val kickerRank = kicker.rank
                        val otherKickerRank = twoPairsCombination.kicker.rank
                        when {
                            kickerRank > otherKickerRank -> 1
                            kickerRank < otherKickerRank -> -1
                            kickerRank == otherKickerRank -> 0
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
            TwoPairs(it)
        }

        private fun List<Card>.isCombination(): Boolean =
            map { it.rank }.sorted().windowed(2, 1).filter { it.isDouble() }.size >= 2

        private fun List<Rank>.isDouble() = size == 2 && toSet().size == 1
    }
}