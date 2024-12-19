package dev.airon.movieapp.presentation.ui.main.bottomBar.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.databinding.FragmentHomeBinding
import dev.airon.movieapp.presentation.adapter.home.GenreMovieAdapter
import dev.airon.movieapp.presentation.model.GenrePresentation
import dev.airon.movieapp.presentation.viewmodel.home.HomeViewModel
import dev.airon.movieapp.utils.StateView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var genreMovieAdapter: GenreMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //initListener()
        initRecycler()
        getGenres()

    }



    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter{ genreId ->
            val action = HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId)
            findNavController().navigate(action)

        }
        with(binding.recyclerGenres) {
            setHasFixedSize(true)
            adapter = genreMovieAdapter
            layoutManager = LinearLayoutManager(requireContext())
            post {
                invalidate()
                requestLayout()
            }
        }
    }


    private fun getMovieByGenre(genres: List<GenrePresentation>) {

        val genreMutableList = genres.toMutableList()

        genreMutableList.forEachIndexed { index, genre ->
            viewModel.getMovieByGenre(genre.id).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                        binding.progressBar.isVisible = true
                    }

                    is StateView.Success -> {
                        binding.progressBar.isVisible = false
                        genreMutableList[index] = genre.copy(moveis = stateView.data?.take(6))

                        lifecycleScope.launch {
                            delay(1000)
                            genreMovieAdapter.submitList(genreMutableList.toList())
                        }
                    }

                    is StateView.Error -> {

                            binding.progressBar.isVisible = false
                    }
                }

            }
        }

    }



    private fun getGenres() {

        viewModel.getGenres().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    val genres = stateView.data ?: emptyList()
                    genreMovieAdapter.submitList(genres) // Envia uma lista inicial vazia ou apenas os gêneros
                    getMovieByGenre(genres)
                }

                is StateView.Error -> {}
            }

        }
        binding.recyclerGenres.post {
            binding.recyclerGenres.scrollToPosition(0)
            binding.recyclerGenres.visibility = View.VISIBLE
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}