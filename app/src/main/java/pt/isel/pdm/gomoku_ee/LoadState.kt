package pt.isel.pdm.gomoku_ee

import java.lang.IllegalStateException

sealed class LoadState<out T> {
    object Idle : LoadState<Nothing>()
    object Loading : LoadState<Nothing>()
    data class Loaded<T>(val result: Result<T>) : LoadState<T>()
}
fun <T> LoadState<T>.resetToIdle() = LoadState.Idle
fun <T> LoadState<T>.loading() = LoadState.Loading
fun <T> LoadState<T>.complete(result: Result<T>) = LoadState.Loaded(result)

fun <T> LoadState<T>.getOrNull(): T? = when (this) {
    is LoadState.Loaded -> result.getOrThrow()
    else -> throw IllegalStateException("No value available")
}

fun <T> LoadState<T>.get(): T = when (this) {
    is LoadState.Loaded -> result.getOrThrow()
    else -> throw IllegalStateException("No value available")
}