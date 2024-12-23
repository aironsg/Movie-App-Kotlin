package dev.airon.movieapp.data.repository.movie

import dev.airon.movieapp.data.datasource.remote.api.ServiceAPI
import dev.airon.movieapp.data.model.response.GenresResponse
import dev.airon.movieapp.data.model.response.MovieResponse
import dev.airon.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(private val serviceAPI: ServiceAPI) :  MovieRepository {

    override suspend fun getGenres(apiKey: String, language: String?): GenresResponse {
        return serviceAPI.getGenres(
            apiKey = apiKey,
            language = language
        )
    }

    override suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse> {
        return serviceAPI.getMoviesByGenre(
            apiKey = apiKey,
            language = language,
            genreId = genreId
        ).results ?: emptyList()
    }
}