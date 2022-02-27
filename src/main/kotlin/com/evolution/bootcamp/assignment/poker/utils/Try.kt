package com.evolution.bootcamp.assignment.poker.utils

sealed class Try<out T> {
    data class Success<T>(val data: T) : Try<T>()
    data class Failure(val t: Throwable) : Try<Nothing>()

    inline fun <R> map(transform: (T) -> R) = when (this) {
        is Failure -> Failure(t)
        is Success -> runSafely { transform(data) }
    }

    inline fun <R> fold(onSuccess: (T) -> R, onFailure: (Throwable) -> R) = when (this) {
        is Failure -> onFailure(t)
        is Success -> onSuccess(data)
    }

    companion object {

        inline fun <R> runSafely(block: () -> R) = try {
            Success(block())
        } catch (t: Throwable) {
            Failure(t)
        }
    }
}
