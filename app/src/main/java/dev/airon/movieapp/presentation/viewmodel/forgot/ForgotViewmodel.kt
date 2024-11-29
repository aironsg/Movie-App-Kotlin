package dev.airon.movieapp.presentation.viewmodel.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.airon.movieapp.domain.usecase.auth.ForgotUseCase
import dev.airon.movieapp.utils.StateView
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class ForgotViewmodel @Inject constructor(private val forgotUseCase: ForgotUseCase) : ViewModel() {

    fun forgot(email : String)  = liveData(Dispatchers.IO){
        try {
            emit(StateView.Loading())
            forgotUseCase.invoke(email)
            emit(StateView.Success(Unit))
        }catch (ex : Exception){
            emit(StateView.Error(ex.message))

        }
    }
}