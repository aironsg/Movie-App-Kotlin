package dev.airon.movieapp.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.airon.movieapp.domain.usecase.movie.GetGenresUseCase
import dev.airon.movieapp.domain.usecase.movie.GetMovieByGenreUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getMovieByGenreUseCase: GetMovieByGenreUseCase
) : ViewModel() {


}