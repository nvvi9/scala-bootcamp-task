package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

sealed class Combination(val cards: List<Card>, private val combinationStrength: Int) : Comparable<Combination> {

    abstract fun compareWith(other: Combination): Int?

    override fun compareTo(other: Combination): Int = when {
        combinationStrength < other.combinationStrength -> -1
        combinationStrength > other.combinationStrength -> 1
        combinationStrength == other.combinationStrength -> this.compareWith(other) ?: throw IllegalStateException()
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

