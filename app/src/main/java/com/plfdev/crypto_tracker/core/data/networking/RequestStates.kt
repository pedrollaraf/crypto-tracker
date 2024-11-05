package com.plfdev.crypto_tracker.core.data.networking

import com.plfdev.crypto_tracker.core.domain.util.NetworkError

sealed class RequestStates<out T> {
    data class Success<out T>(val data: T) : RequestStates<T>()
    data object Loading : RequestStates<Nothing>()
    data object Initial : RequestStates<Nothing>()
    data class Failure(val error: NetworkError?) : RequestStates<Nothing>()
}