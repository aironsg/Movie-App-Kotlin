package dev.airon.movieapp.presentation.ui.auth.forgot

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
import dev.airon.movieapp.databinding.FragmentForgotBinding
import dev.airon.movieapp.presentation.viewmodel.forgot.ForgotViewmodel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.hideKeyboard
import dev.airon.movieapp.utils.initToolbar
import dev.airon.movieapp.utils.isValidEmail
import dev.airon.movieapp.utils.setupKeyboardDismissal
import dev.airon.movieapp.utils.showSnackBar

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        view.setupKeyboardDismissal(this)
        initListener()

    }


    private fun initListener() {
        binding.btnSend.setOnClickListener {
            hideKeyboard()
            validateData()
        }
        Glide.with(requireContext()).load(R.drawable.loading).into(binding.progressBar)
    }

    private fun validateData() {

        val email = binding.editEmail.text.toString().trim()
        binding.editEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding.root.clearFocus()
                hideKeyboard()
            }
        }
        hideKeyboard()
        if (email.isNotEmpty()) {
            if (email.isValidEmail()) {
                forgot(email)
            } else {
                showSnackBar(message = R.string.invalid_email)
            }
        } else {
            showSnackBar(message = R.string.text_email_empty)
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
                    showSnackBar(message = R.string.text_forgot_password_success)
                    findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
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