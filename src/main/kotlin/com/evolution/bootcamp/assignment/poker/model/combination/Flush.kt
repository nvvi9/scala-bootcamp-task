package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class Flush private constructor(cards: List<Card>) : Combination(cards, 5) {

    override fun compareWith(other: Combination): Int? = (other as? Flush)?.let { flushCombination ->
        val zippedRanks = cards.map { it.rank }.sorted()
            .zip(flushCombination.cards.map { it.rank }.sorted())

        when {
            zippedRanks.all { it.first == it.second } -> 0
            zippedRanks.indexOfFirst { it.first > it.second } > zippedRanks.indexOfFirst { it.first < it.second } -> 1
            else -> -1
        }
    }

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            Flush(it)
        }

        private fun List<Card>.isCombination(): Boolean = map { it.suit }.toSet().size == 1
    }
}