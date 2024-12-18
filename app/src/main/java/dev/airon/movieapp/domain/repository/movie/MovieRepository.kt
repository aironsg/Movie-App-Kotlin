package dev.airon.movieapp.domain.repository.movie

import dev.airon.movieapp.data.model.response.GenresResponse
import dev.airon.movieapp.data.model.response.MovieResponse

interface MovieRepository {


    suspend fun getGenres(apiKey: String, language: String?): GenresResponse

    suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieResponse>
}