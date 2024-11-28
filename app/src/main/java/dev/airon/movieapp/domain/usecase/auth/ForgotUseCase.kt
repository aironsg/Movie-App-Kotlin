package dev.airon.movieapp.domain.usecase.auth

import dev.airon.movieapp.domain.repository.auth.FirebaseAuthentication

class ForgotUseCase constructor(private val auth: FirebaseAuthentication) {
    suspend operator fun invoke(email: String) = auth.forgotPassword(email)
}