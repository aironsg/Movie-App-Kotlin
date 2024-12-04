package dev.airon.movieapp.presentation.ui.auth.forgot

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
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
                    sendMail("E-mail enviado para $email")
                    Handler(Looper.getMainLooper()).postDelayed({
                        showSnackBar(message = R.string.txt_send_email_success)
                        findNavController().navigate(R.id.action_forgotFragment_to_loginFragment)
                    }, 3000)
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                    showSnackBar(FirebaseHelper.validError(stateView.message ?: ""))

                }
            }
        }
    }

    private fun sendMail(email: String) {
        binding.flyingMailIcon.visibility = View.VISIBLE
        val translationY = -binding.btnSend.y - binding.btnSend.height * 2

        binding.flyingMailIcon
            .animate()
            .translationY(translationY)
            .scaleX(0.5f)  // Reduz a escala no eixo X
            .scaleY(0.5f)  // Reduz a escala no eixo Y
            .alpha(0f)     // Desaparece gradualmente
            .setInterpolator(AccelerateInterpolator()) // Efeito de aceleração
            .setDuration(2500) // Duração mais longa para suavidade
            .withEndAction {
                binding.flyingMailIcon.visibility = View.INVISIBLE
                binding.flyingMailIcon.alpha = 1f // Restaura opacidade
                binding.flyingMailIcon.scaleX = 1f // Restaura escala
                binding.flyingMailIcon.scaleY = 1f
                binding.flyingMailIcon.translationY = 0f // Restaura posição
            }
            .start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}