package com.evolution.bootcamp.assignment.poker.model

interface Parser<T, R> {
    fun from(value: T): R
}