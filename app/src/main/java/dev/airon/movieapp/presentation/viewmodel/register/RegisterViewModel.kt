package dev.airon.movieapp.presentation.viewmodel.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.airon.movieapp.domain.usecase.auth.RegisterUseCase
import dev.airon.movieapp.utils.StateView
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerUseCase: RegisterUseCase) :
    ViewModel(

    ) {
    fun register(email: String, password: String) = liveData(Dispatchers.IO) {
        try {
            emit(StateView.Loading())
            registerUseCase.invoke(email, password)
            emit(StateView.Success(Unit))
        } catch (ex: Exception) {
            emit(StateView.Error(ex.message))
        }


    }
}