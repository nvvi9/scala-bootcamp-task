package com.evolution.bootcamp.assignment.poker.utils

interface Parser<T, R> {
    fun from(value: T): R
}