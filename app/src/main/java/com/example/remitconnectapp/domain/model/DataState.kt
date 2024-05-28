package com.example.remitconnectapp.domain.model

sealed class DataState<out T> {
    data object Idle : DataState<Nothing>()

    data object Loading : DataState<Nothing>()

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val exception: Throwable? = null) : DataState<Nothing>()
}