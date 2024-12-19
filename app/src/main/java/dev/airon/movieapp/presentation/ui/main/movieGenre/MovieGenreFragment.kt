package dev.airon.movieapp.presentation.ui.main.movieGenre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.airon.movieapp.databinding.FragmentMovieGenreBinding
import dev.airon.movieapp.presentation.adapter.home.MovieAdapter
import dev.airon.movieapp.presentation.viewmodel.movieGenre.MovieGenreViewModel
import dev.airon.movieapp.utils.StateView
import dev.airon.movieapp.utils.initToolbar

@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private var _binding: FragmentMovieGenreBinding? = null
    private val binding get() = _binding!!
    private val args: MovieGenreFragmentArgs by navArgs()
    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieGenreViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieGenreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initRecycler()
        getMovieByGenre()
    }

    private fun initRecycler() {

        movieAdapter = MovieAdapter(requireContext())

        with(binding.recyclerMovie) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(false)
            adapter = movieAdapter
        }
    }

    private fun getMovieByGenre() {
        viewModel.getMovieByGenre(args.genreId).observe(viewLifecycleOwner) { stateView ->
            when (stateView) {
                is StateView.Loading -> {
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                   movieAdapter.submitList(stateView.data)
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }

        }


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}