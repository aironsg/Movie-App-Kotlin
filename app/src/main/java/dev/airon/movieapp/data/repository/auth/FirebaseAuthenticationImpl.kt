package dev.airon.movieapp.data.repository.auth

import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import dev.airon.movieapp.domain.repository.auth.FirebaseAuthentication
import javax.inject.Inject
import kotlin.coroutines.suspendCoroutine

class FirebaseAuthenticationImpl @Inject constructor (private val auth: FirebaseAuth) : FirebaseAuthentication {

    override suspend fun login(email: String, password: String) {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(Unit))
                    TODO("transferir ususario para a tela HOME")
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }

                }
            }
        }
    }

    override suspend fun register(email: String, password: String) {
        return suspendCoroutine { continuation ->
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(Unit))
                    TODO("transferir ususario para a tela HOME")
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }

                }
            }
        }

    }

    override suspend fun forgotPassword(email: String) {
        return suspendCoroutine { continuation ->
            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resumeWith(Result.success(Unit))
                } else {
                    task.exception?.let {
                        continuation.resumeWith(Result.failure(it))
                    }

                }
            }
        }
    }
}