package com.evolution.bootcamp.assignment.poker.model

sealed class Combination {

    companion object {

    }
}

object StraightFlush : Combination()
object FourOfAKind : Combination()
object FullHouse : Combination()
object Flush : Combination()
object Straight : Combination()
object ThreeOfAKind : Combination()
object TwoPairs : Combination()
object Pair : Combination()
object HighCard : Combination()