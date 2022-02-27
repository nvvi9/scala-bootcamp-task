package com.evolution.bootcamp.assignment.poker.boards

import com.evolution.bootcamp.assignment.poker.model.combination.Combination
import com.evolution.bootcamp.assignment.poker.model.hand.Hand

class FiveCardDrawBoard(hands: List<Hand>) : Board(emptyList(), hands) {

    override val sortedByStrength
        get() = hands.map { it to Combination.from(it.cards) }
            .sortedBy { it.second }
}