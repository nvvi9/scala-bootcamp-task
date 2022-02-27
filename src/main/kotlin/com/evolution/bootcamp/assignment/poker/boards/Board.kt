package com.evolution.bootcamp.assignment.poker.boards

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.combination.Combination
import com.evolution.bootcamp.assignment.poker.model.hand.Hand
import com.evolution.bootcamp.assignment.poker.utils.Parser

sealed class Board(val boardCards: List<Card>, val hands: List<Hand>) {

    abstract val sortedByStrength: List<Pair<Hand, Combination>>

    val sortedByStrengthWithWeight: List<Pair<Int, Hand>>
        get() = sortedByStrength.fold(listOf<Triple<Int, Hand, Combination>>()) { acc, pair ->
            acc + (acc.lastOrNull()?.let {
                if (it.third.compareWith(pair.second) == 0) {
                    listOf(Triple(it.first, pair.first, pair.second))
                } else {
                    listOf(Triple(it.first + 1, pair.first, pair.second))
                }
            } ?: listOf(Triple(0, pair.first, pair.second)))
        }.map { it.first to it.second }

    companion object : Parser<String, Board> {

        override fun from(value: String): Board {
            val values = value.split(" ")
            return when (values.first()) {
                "five-card-draw" -> FiveCardDrawBoard(values.drop(1).map { Hand.from(it) })
                "texas-holdem" -> TexasHoldemBoard(
                    values[1].chunked(2).map { Card.from(it) },
                    values.drop(2).map { Hand.from(it) }
                )
                "omaha-holdem" -> OmahaHoldemBoard(
                    values[1].chunked(2).map { Card.from(it) },
                    values.drop(2).map { Hand.from(it) }
                )
                else -> throw IllegalStateException()
            }
        }
    }
}

