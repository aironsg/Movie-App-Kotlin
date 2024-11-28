package dev.airon.movieapp.domain.usecase.auth

import dev.airon.movieapp.domain.repository.auth.FirebaseAuthentication
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val auth : FirebaseAuthentication) {
    suspend operator fun invoke(email:String, password:String) = auth.login(email, password)
}