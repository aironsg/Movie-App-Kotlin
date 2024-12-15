package dev.airon.movieapp.data.mapper

import dev.airon.movieapp.data.model.response.GenreResponse
import dev.airon.movieapp.data.model.response.MovieResponse
import dev.airon.movieapp.domain.model.Genre
import dev.airon.movieapp.domain.model.Movie
import dev.airon.movieapp.presentation.model.GenrePresentation

fun GenreResponse.toDomain(): Genre {
    return Genre(id = id, name = name)
}

fun MovieResponse.toDomain(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        genreIds = genreIds,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun Genre.toPresentation(): GenrePresentation {
    return GenrePresentation(id = id, name = name, moveis = emptyList())
}