package dev.airon.movieapp.utils

sealed class StateView<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T, message: String? = null) : StateView<T>(data = data,message = message)
    class Error<T>(message: String?) : StateView<T>( message = message)
    class Loading<T>: StateView<T>(data = null, message = null)
}