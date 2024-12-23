package dev.airon.movieapp.presentation.ui.auth.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.R
import dev.airon.movieapp.databinding.FragmentHomeAuthBinding

@AndroidEntryPoint
class HomeAuthFragment : Fragment() {
    private var _binding: FragmentHomeAuthBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAuthBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {
        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_homeAuthFragment_to_loginFragment)
        }
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_homeAuthFragment_to_registerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}