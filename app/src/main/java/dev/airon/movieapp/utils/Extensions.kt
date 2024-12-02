package dev.airon.movieapp.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import dev.airon.movieapp.R


fun Fragment.initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = true) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).title = ""
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
        toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }
    }

fun Fragment.hideKeyboard() {
   val imm = getSystemService(requireContext(), InputMethodManager::class.java) as InputMethodManager
    val view = requireActivity().currentFocus ?: View(requireContext())
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
    return this.matches(Regex(passwordPattern))
}



