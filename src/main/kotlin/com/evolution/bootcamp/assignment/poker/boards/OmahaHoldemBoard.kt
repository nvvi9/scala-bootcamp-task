package com.evolution.bootcamp.assignment.poker.boards

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.model.combination.Combination
import com.evolution.bootcamp.assignment.poker.model.hand.Hand

class OmahaHoldemBoard(boardCards: List<Card>, hands: List<Hand>) : Board(boardCards, hands) {

    override val sortedByStrength: List<Pair<Hand, Combination>>
        get() = hands.map { it to bestCombinationForHand(it) }
            .sortedBy { it.second }

    private fun bestCombinationForHand(hand: Hand) =
        getHandPairs(hand).flatMap { fromHand ->
            boardTriplets.map { fromBoard ->
                Combination.from(fromBoard + fromHand)
            }
        }.maxOrNull() ?: throw IllegalStateException()

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

    private fun getHandPairs(hand: Hand): List<List<Card>> = mutableListOf<List<Card>>().apply {
        for (i in 0 until hand.cards.size - 1) {
            for (j in i until hand.cards.size - 1) {
                add(listOf(hand.cards[i], hand.cards[j + 1]))
            }
        }
    }
}