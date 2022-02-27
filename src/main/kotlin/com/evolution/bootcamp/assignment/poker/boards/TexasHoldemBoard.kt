package com.evolution.bootcamp.assignment.poker.boards

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.combination.Combination
import com.evolution.bootcamp.assignment.poker.model.hand.Hand

class TexasHoldemBoard(boardCards: List<Card>, hands: List<Hand>) : Board(boardCards, hands) {

    override val sortedByStrength
        get() = hands.map { it to bestCombinationForHand(it) }
            .sortedBy { it.second }

    private val boardTriplets: List<List<Card>>
        get() = mutableListOf<List<Card>>().apply {
            for (i in 0 until (boardCards.size - 2)) {
                for (j in (i + 1) until (boardCards.size - 1)) {
                    for (k in (j + 1) until boardCards.size) {
                        add(listOf(boardCards[i], boardCards[j], boardCards[k]))
                    }
                }
            }
        }

    private fun bestCombinationForHand(hand: Hand): Combination =
        boardTriplets.map { it + hand.cards }
            .map { Combination.from(it) }.maxOrNull()
            ?: throw IllegalStateException()
}