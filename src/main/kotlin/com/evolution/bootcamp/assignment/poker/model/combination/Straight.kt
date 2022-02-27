package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class Straight private constructor(cards: List<Card>) : Combination(cards, 4) {

    override fun compareWith(other: Combination): Int? = (other as? Straight)?.let { straightCombination ->
        val maxRank = cards.maxByOrNull { it.rank }?.rank ?: throw IllegalStateException()
        val otherMaxRank = straightCombination.cards.maxByOrNull { it.rank }?.rank ?: throw IllegalStateException()

        when {
            maxRank > otherMaxRank -> 1
            maxRank < otherMaxRank -> -1
            maxRank == otherMaxRank -> 0
            else -> throw IllegalStateException()
        }
    }

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination? = value.takeIf { it.isCombination() }?.let {
            Straight(it)
        }

        private fun List<Card>.isCombination(): Boolean =
            map { it.rank }.sorted().windowed(2, 1).all { it[1] - it[0] == 1 }
    }
}