package com.evolution.bootcamp.assignment.poker.model.combination

import com.evolution.bootcamp.assignment.poker.model.cards.Card
import com.evolution.bootcamp.assignment.poker.utils.Parser

class HighCard private constructor(cards: List<Card>) : Combination(cards, 0) {

    override fun compareWith(other: Combination): Int? = (other as? HighCard)?.let { highCardCombination ->
        val zippedRanks = cards.map { it.rank }.sorted().zip(highCardCombination.cards.map { it.rank }.sorted())

        when {
            zippedRanks.all { it.first == it.second } -> 0
            zippedRanks.indexOfFirst { it.first > it.second } > zippedRanks.indexOfFirst { it.first < it.second } -> 1
            else -> -1
        }
    }

    companion object : Parser<List<Card>, Combination?> {

        override fun from(value: List<Card>): Combination = HighCard(value)
    }
}