package dev.airon.movieapp.presentation.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.hellodev.netflix.util.FirebaseHelper
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.R
import dev.airon.movieapp.databinding.FragmentRegisterBinding
import dev.airon.movieapp.presentation.MainActivity
import dev.airon.movieapp.presentation.viewmodel.register.RegisterViewModel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.hideKeyboard
import dev.airon.movieapp.utils.initToolbar
import dev.airon.movieapp.utils.isValidEmail
import dev.airon.movieapp.utils.isValidPassword
import dev.airon.movieapp.utils.setupKeyboardDismissal
import dev.airon.movieapp.utils.showSnackBar

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        view.setupKeyboardDismissal(this)
        initListener()
    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener {
            hideKeyboard()
            validateData()
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

        if (!email.isValidEmail()){
            showSnackBar(message = R.string.invalid_email_register_fragment)
            return
        }
        if(email.isEmpty()){
            showSnackBar(message = R.string.text_email_empty)
            return
        }
        if (!password.isValidEmail()){
            showSnackBar(message = R.string.strong_password_register_fragment)
            return
        }
        if(password.isEmpty()){
            showSnackBar(message = R.string.text_password_empty)
            return
        }

        registerUser(email,password)
    }

    private fun registerUser(email: String, password: String) {
        registerViewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    showSnackBar(message = R.string.text_register_success)
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