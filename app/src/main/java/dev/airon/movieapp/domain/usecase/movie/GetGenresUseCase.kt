package dev.airon.movieapp.domain.usecase.movie

import dev.airon.movieapp.data.mapper.toDomain
import dev.airon.movieapp.domain.model.Genre
import dev.airon.movieapp.domain.repository.movie.MovieRepository
import javax.inject.Inject

class GetGenresUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(apiKey: String, language: String?): List<Genre> {
        return repository.getGenres(
            apiKey = apiKey
            , language = language
        ).genres?.mapNotNull { it.toDomain() } ?: emptyList()
    }

}