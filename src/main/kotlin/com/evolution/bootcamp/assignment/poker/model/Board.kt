package com.evolution.bootcamp.assignment.poker.model

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.hand.Hand

sealed class Board(val boardCards: List<Card>, val hands: List<Hand>) {

    abstract val sortedByStrength: List<Hand>

    companion object : Parser<String, Board> {

        override fun from(value: String): Board {
            val values = value.split(" ")
            return when (values.first()) {
                "five-card-draw" -> FiveCardDrawBoard(values.drop(1).map { Hand.from(it) })
                else -> throw IllegalStateException()
            }
        }
    }
}

class FiveCardDrawBoard(hands: List<Hand>) : Board(emptyList(), hands) {

    override val sortedByStrength = hands.map { it to Combination.from(it.cards) }.sortedBy { it.second }.map {
        println(it)
        it.first
    }
}