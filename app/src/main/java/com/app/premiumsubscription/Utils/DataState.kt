package com.app.premiumsubscription.Utils

class DataState<T>(
    val loading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null
) {
    companion object {
        fun <T> loading(): DataState<T> = DataState(true)
        fun <T> success(data: T? = null): DataState<T> = DataState(false, data)
        fun <T> error(errorMessage: String): DataState<T> = DataState(false, null, errorMessage)
    }
}
