package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.cards.Rank
import com.evolution.bootcamp.assignment.poker.utils.Parser

class Pair private constructor(cards: List<Card>) : Combination(cards, 1) {

    private val pair = cards.sortedBy { it.rank }.windowed(2, 1)
        .find { it.map { it.rank }.isDouble() }
        ?: throw IllegalStateException()

    private val kickers = cards - pair

    override fun compareWith(other: Combination): Int? = (other as? Pair)?.let { pairCombination ->
        val pairRank = pair.first().rank
        val otherPairRank = pairCombination.pair.first().rank

        when {
            pairRank > otherPairRank -> 1
            pairRank < otherPairRank -> -1
            pairRank == otherPairRank -> {
                val zippedRanks = kickers.map { it.rank }.sorted()
                    .zip(pairCombination.kickers.map { it.rank }.sorted())
                when {
                    zippedRanks.all { it.first == it.second } -> 0
                    zippedRanks.indexOfFirst { it.first > it.second } > zippedRanks.indexOfFirst { it.first < it.second } -> 1
                    else -> -1
                }
            }
            else -> throw IllegalStateException()
        }
    }

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            Pair(it)
        }

        private fun List<Card>.isCombination(): Boolean =
            map { it.rank }.sorted().windowed(2, 1).any { it.isDouble() }

        private fun List<Rank>.isDouble() = size == 2 && toSet().size == 1
    }
}