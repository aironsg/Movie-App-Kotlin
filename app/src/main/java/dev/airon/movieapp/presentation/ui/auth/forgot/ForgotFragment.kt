package dev.airon.movieapp.presentation.ui.auth.forgot

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.R
import dev.airon.movieapp.databinding.FragmentForgotBinding
import dev.airon.movieapp.presentation.viewmodel.forgot.ForgotViewmodel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.hideKeyboard
import dev.airon.movieapp.utils.initToolbar
import dev.airon.movieapp.utils.isValidEmail

@AndroidEntryPoint
class ForgotFragment : Fragment() {

    private var _binding: FragmentForgotBinding? = null
    private val binding get() = _binding!!
    private val forgotViewModel: ForgotViewmodel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()

        view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                val currentFocusView = activity?.currentFocus
                if (currentFocusView is EditText) {
                    val outRect = Rect()
                    currentFocusView.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        currentFocusView.clearFocus()
                        hideKeyboard()
                    }
                }
            }
            false
        }
    }

    private fun initListener() {
        binding.btnSend.setOnClickListener {
            validateData()
        }
        Glide.with(requireContext()).load(R.drawable.loading).into(binding.progressBar)
    }

    private fun validateData() {

        binding.editEmail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                val email = binding.editEmail.text.toString().trim()
                if (email.isValidEmail()){
                    forgot(email)
                }else{
                    binding.editEmail.error = "Formato de e-mail inválido"
                }
            }
        }


    }

    private fun forgot(email: String) {
        forgotViewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    //TODO: navegar para tela LOGIN
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(), stateView.message, Toast.LENGTH_SHORT).show()

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}