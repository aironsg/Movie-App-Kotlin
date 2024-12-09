package dev.airon.movieapp.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.hellodev.netflix.util.FirebaseHelper
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.R
import dev.airon.movieapp.databinding.FragmentLoginBinding
import dev.airon.movieapp.presentation.ui.main.activity.MainActivity
import dev.airon.movieapp.presentation.viewmodel.login.LoginViewModel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.hideKeyboard
import dev.airon.movieapp.utils.initToolbar
import dev.airon.movieapp.utils.isValidEmail
import dev.airon.movieapp.utils.setupKeyboardDismissal
import dev.airon.movieapp.utils.showSnackBar

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        view.setupKeyboardDismissal(this)
        initListener()
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            hideKeyboard()
            validateData()
        }

        binding.btnForgot.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotFragment)
        }

        Glide.with(requireContext()).load(R.drawable.loading).into(binding.progressBar)
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString()
        binding.editEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.root.clearFocus()
                hideKeyboard()
            }
        }
        binding.edtPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.root.clearFocus()
                hideKeyboard()
            }
        }

        if (email.isNotEmpty()) {
            if (email.isValidEmail()) {
                if (password.isNotEmpty()) {
                    login(email, password)
                } else {
                    showSnackBar(message = R.string.text_password_empty)
                }
            } else {
                showSnackBar(message = R.string.invalid_email)
            }
        } else {
            showSnackBar(message = R.string.text_email_empty)
        }

    }

    private fun login(email: String, password: String) {

        loginViewModel.login(email, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    showSnackBar(message = R.string.text_login_success)
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()

                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false

                    showSnackBar(FirebaseHelper.validError(stateView.message ?: ""))

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}