package dev.airon.movieapp.presentation.ui.auth.forgot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.databinding.FragmentForgotBinding
import dev.airon.movieapp.presentation.viewmodel.forgot.ForgotViewmodel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.initToolbar

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
        initListener()
    }

    private fun initListener() {
        binding.btnSend.setOnClickListener {
            validateData()

        }
    }

    private fun validateData() {
        val email = binding.editEmail.text.toString().trim()
        if (email.isNotEmpty()) {
            forgot(email)
        } else {
            Toast.makeText(context, "Preencha o email", Toast.LENGTH_SHORT).show()
        }

    }

    private fun forgot(email: String) {
        forgotViewModel.forgot(email).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is StateView.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    //TODO: navegar para tela LOGIN
                }

                is StateView.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
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