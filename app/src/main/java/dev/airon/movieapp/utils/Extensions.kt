package dev.airon.movieapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import dev.airon.movieapp.R

//botão de retorno na toolbar
fun Fragment.initToolbar(toolbar: Toolbar, homeAsUpEnabled: Boolean = true) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(homeAsUpEnabled)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressed()
    }
}

//fechar teclado quando clicar fora
fun Fragment.hideKeyboard() {
    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = requireActivity().currentFocus ?: View(requireContext())
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

//fechar teclado quando o focus mudar
@SuppressLint("ClickableViewAccessibility")
fun View.setupKeyboardDismissal(fragment: Fragment) {
    setOnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_DOWN) {
            val currentFocusView = fragment.requireActivity().currentFocus
            if (currentFocusView is EditText) {
                val outRect = Rect()
                currentFocusView.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    currentFocusView.clearFocus()
                    fragment.hideKeyboard()
                }
            }
        }
        false
    }
}



//validar email
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

//validar senha
fun String.isValidPassword(): Boolean {
    val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
    return this.matches(Regex(passwordPattern))
}



