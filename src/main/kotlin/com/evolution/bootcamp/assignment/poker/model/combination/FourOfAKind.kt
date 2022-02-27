package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class FourOfAKind private constructor(cards: List<Card>) : Combination(cards, 7) {

    private val quadruplet = cards.sortedBy { it.rank }.run {
        drop(1).takeIf { it.map { it.rank }.toSet().size == 1 }
            ?: dropLast(1).takeIf { it.map { it.rank }.toSet().size == 1 }
    } ?: throw IllegalStateException()

    private val kicker = (cards - quadruplet).first()

    override fun compareWith(other: Combination): Int? = (other as? FourOfAKind)?.let { fourOfAKindCombination ->
        val maxRank = quadruplet.first().rank
        val otherMaxRank = fourOfAKindCombination.quadruplet.first().rank

        when {
            maxRank > otherMaxRank -> 1
            maxRank < otherMaxRank -> -1
            maxRank == otherMaxRank -> {
                val kickerRank = kicker.rank
                val otherKickerRank = fourOfAKindCombination.kicker.rank
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

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>) = value.takeIf { it.isCombination() }?.let {
            FourOfAKind(it)
        }

        private fun List<Card>.isCombination() = map { it.rank }.sorted().let {
            it.drop(1).toSet().size == 1 || it.dropLast(1).toSet().size == 1
        }
    }
}