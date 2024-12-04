package br.com.hellodev.netflix.util

import com.google.firebase.auth.FirebaseAuth
import dev.airon.movieapp.R

class FirebaseHelper {

    companion object {
        fun getAuth() = FirebaseAuth.getInstance()

        fun isAuthenticated() = getAuth().currentUser != null

        fun getUserId() = FirebaseAuth.getInstance().currentUser?.uid ?: ""

        fun validError(error: String): Int {
            return when {
                error.contains("There is no user record corresponding to this identifier") -> {
                    R.string.account_incorrect
                }
                error.contains("The supplied auth credential is incorrect, malformed or has expired.") -> {
                    R.string.account_incorrect
                }
                error.contains("The email address is badly formatted") -> {
                    R.string.invalid_email
                }
                error.contains("The password is invalid") -> {
                    R.string.invalid_password
                }
                error.contains("The email address is already in use by another account") -> {
                    R.string.email_in_use
                }
                error.contains("Password should be at least 6 characters") -> {
                    R.string.strong_password
                }
                else -> {
                    R.string.error_generic
                }
            }
        }
    }

}