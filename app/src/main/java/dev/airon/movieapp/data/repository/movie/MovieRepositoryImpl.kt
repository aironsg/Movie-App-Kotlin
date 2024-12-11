package dev.airon.movieapp.data.repository.movie

import dev.airon.movieapp.data.datasource.remote.api.ServiceAPI
import dev.airon.movieapp.data.model.GenresResponse
import dev.airon.movieapp.data.model.dto.MovieDTO
import dev.airon.movieapp.domain.repository.movie.MovieRepository
import dev.airon.movieapp.utils.Constants
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val serviceAPI: ServiceAPI) :  MovieRepository {

    override suspend fun getGenres(apiKey: String, language: String?): GenresResponse {
        return serviceAPI.getGenres(
            apiKey = apiKey,
            language = Constants.Movie.LANGUAGE
        )
    }

    override suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieDTO> {
        return serviceAPI.getMoviesByGenre(
            apiKey = apiKey,
            language = Constants.Movie.LANGUAGE,
            genreId = genreId
        ).results ?: emptyList()
    }
}