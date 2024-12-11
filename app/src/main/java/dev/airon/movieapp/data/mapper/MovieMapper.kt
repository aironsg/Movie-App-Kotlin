package dev.airon.movieapp.data.mapper

import dev.airon.movieapp.data.model.dto.GenreDTO
import dev.airon.movieapp.data.model.dto.MovieDTO
import dev.airon.movieapp.domain.model.Genre
import dev.airon.movieapp.domain.model.Movie

fun GenreDTO.toDomain(): Genre {
    return Genre(id = id, name = name)
}

fun MovieDTO.toDomain(): Movie{
    return Movie(
        adult = adult,
        backdropPath =backdropPath,
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