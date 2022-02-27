package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class StraightFlush private constructor(cards: List<Card>) : Combination(cards, 8) {

    override fun compareWith(other: Combination): Int? = (other as? StraightFlush)?.let { straightFlushCombination ->
        val maxRank = cards.maxByOrNull { it.rank }?.rank ?: throw IllegalStateException()
        val otherMaxRank = straightFlushCombination.cards.maxByOrNull { it.rank }?.rank ?: throw IllegalStateException()

        when {
            maxRank == otherMaxRank -> 0
            maxRank > otherMaxRank -> 1
            maxRank < otherMaxRank -> -1
            else -> throw IllegalStateException()
        }
    }

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>) = value.takeIf { it.isCombination() }?.let {
            StraightFlush(it)
        }

        private fun List<Card>.isCombination() =
            map { it.suit }.toSet().size == 1
                    && map { it.rank }.sorted().windowed(2, step = 1).all { it[1] - it[0] == 1 }
    }
}