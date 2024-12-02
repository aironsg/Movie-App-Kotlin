package dev.airon.movieapp.presentation.ui.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.R
import dev.airon.movieapp.databinding.FragmentRegisterBinding
import dev.airon.movieapp.presentation.viewmodel.register.RegisterViewModel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.initToolbar

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
        initListener()
    }

    private fun initListener() {
        binding.btnRegister.setOnClickListener { validateData() }
        Glide.with(requireContext()).load(R.drawable.loading).into(binding.progressBar)
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                registerUser(email, password)
            } else {
                Toast.makeText(requireContext(), "Preencha a senha", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Preencha o email", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(email: String, password: String) {
        registerViewModel.register(email, password).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    Toast.makeText(
                        requireContext(),
                        "Usuario cadastrado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    // TODO: navegar para tela HOME
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