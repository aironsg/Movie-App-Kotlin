package dev.airon.movieapp.presentation.ui.main.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.R
import dev.airon.movieapp.databinding.FragmentHomeBinding
import dev.airon.movieapp.presentation.adapter.home.GenreMovieAdapter
import dev.airon.movieapp.presentation.model.GenrePresentation
import dev.airon.movieapp.presentation.viewmodel.home.HomeViewModel
import dev.airon.movieapp.utils.StateView
import kotlinx.coroutines.async
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

    private fun initListener() {

    }

    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter()
        with(binding.recyclerGenres) {
            setHasFixedSize(true)
            adapter = genreMovieAdapter
        }
    }


    private fun getMovieByGenre(genres: List<GenrePresentation>) {

        val genreMutableList = genres.toMutableList()

        genreMutableList.forEachIndexed { index, genre ->
            viewModel.getMovieByGenre(genre.id).observe(viewLifecycleOwner) { stateView ->
                when (stateView) {
                    is StateView.Loading -> {
                        //binding.progressBar.visibility = View.VISIBLE
                    }

                    is StateView.Success -> {
                        genreMutableList[index] = genre.copy(moveis = stateView.data)
                        
                        lifecycleScope.launch {
                            delay(1000)
                            genreMovieAdapter.submitList(genreMutableList)
                        }
                    }

                    is StateView.Error -> {}
                }

            }
        }

    }

    private fun getGenres() {

        viewModel.getGenres().observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    //binding.progressBar.visibility = View.VISIBLE
                }

                is StateView.Success -> {
                    val genres = stateView.data
                    genreMovieAdapter.submitList(genres)
                    getMovieByGenre(genres ?: emptyList())
                }

                is StateView.Error -> {}
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }
}