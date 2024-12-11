package dev.airon.movieapp.domain.usecase.movie

import dev.airon.movieapp.data.mapper.toDomain
import dev.airon.movieapp.domain.model.Genre
import dev.airon.movieapp.domain.model.Movie
import dev.airon.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetMovieByGenreUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(apiKey: String, language: String?, genreId: Int?): List<Movie> {
        return repository.getMoviesByGenre(
            apiKey = apiKey
            , language = language,
            genreId = genreId
        ).map {
            it.toDomain()
        }
    }

}