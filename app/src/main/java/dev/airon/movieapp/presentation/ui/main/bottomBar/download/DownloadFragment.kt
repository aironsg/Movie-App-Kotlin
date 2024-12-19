package dev.airon.movieapp.presentation.ui.main.bottomBar.download

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.databinding.FragmentDownloadBinding

@AndroidEntryPoint
class DownloadFragment : Fragment() {
    private var _binding : FragmentDownloadBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDownloadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener() {

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}