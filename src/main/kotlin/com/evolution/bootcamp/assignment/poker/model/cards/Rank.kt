package com.evolution.bootcamp.assignment.poker.model.cards

import com.evolution.bootcamp.assignment.poker.model.Parser

sealed class Rank(private val weakerThen: Set<Rank>) : Comparable<Rank> {

    override fun compareTo(other: Rank): Int =
        when {
            this == other -> 0
            weakerThen.contains(other) -> -1
            weakerThen.isEmpty() || !weakerThen.contains(other) -> 1
            else -> throw IllegalStateException()
        }

    companion object : Parser<Char, Rank> {

        override fun from(value: Char) = when (value) {
            '6' -> Six
            '7' -> Seven
            '8' -> Eight
            '9' -> Nine
            'T' -> Ten
            'J' -> Jack
            'Q' -> Queen
            'K' -> King
            'A' -> Ace
            else -> throw IllegalStateException("Unknown rank: $value")
        }
    }
}

object Six : Rank(setOf(Seven, Eight, Nine, Ten, Jack, Queen, King, Ace))
object Seven : Rank(setOf(Eight, Nine, Ten, Jack, Queen, King, Ace))
object Eight : Rank(setOf(Nine, Ten, Jack, Queen, King, Ace))
object Nine : Rank(setOf(Ten, Jack, Queen, King, Ace))
object Ten : Rank(setOf(Jack, Queen, King, Ace))
object Jack : Rank(setOf(Queen, King, Ace))
object Queen : Rank(setOf(King, Ace))
object King : Rank(setOf(Ace))
object Ace : Rank(setOf())