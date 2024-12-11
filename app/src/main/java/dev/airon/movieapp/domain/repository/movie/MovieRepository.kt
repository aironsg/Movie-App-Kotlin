package dev.airon.movieapp.domain.repository.movie

import dev.airon.movieapp.data.model.BasePaginationRemote
import dev.airon.movieapp.data.model.GenresResponse
import dev.airon.movieapp.data.model.dto.MovieDTO

interface MovieRepository {


    suspend fun getGenres(apiKey: String, language: String?): GenresResponse

    suspend fun getMoviesByGenre(
        apiKey: String,
        language: String?,
        genreId: Int?
    ): List<MovieDTO>
}