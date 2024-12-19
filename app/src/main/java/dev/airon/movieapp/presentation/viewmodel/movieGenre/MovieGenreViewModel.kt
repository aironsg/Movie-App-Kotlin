package dev.airon.movieapp.presentation.viewmodel.movieGenre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.airon.movieapp.BuildConfig.API_KEY
import dev.airon.movieapp.domain.usecase.movie.GetGenresUseCase
import dev.airon.movieapp.domain.usecase.movie.GetMovieByGenreUseCase
import dev.airon.movieapp.utils.Constants.Movie.LANGUAGE
import dev.airon.movieapp.utils.StateView
import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MovieGenreViewModel @Inject constructor(private val getMovieByGenreUseCase: GetMovieByGenreUseCase) :
    ViewModel() {

    fun getMovieByGenre(genreId: Int) = liveData(Dispatchers.IO){
        try {
            emit(StateView.Loading())

            val movies = getMovieByGenreUseCase.invoke(
                apiKey = API_KEY,
                language = LANGUAGE,
                genreId =  genreId
            )
            emit(StateView.Success(data = movies))
        }catch (e: HttpException){
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }catch (e: Exception){
            e.printStackTrace()
            emit(StateView.Error(message = e.message))
        }
    }
}