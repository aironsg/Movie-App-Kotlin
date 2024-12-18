package dev.airon.movieapp.presentation.model

import android.os.Parcelable
import dev.airon.movieapp.domain.model.Movie
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenrePresentation(
    val id: Int,
    val name: String?,
    val moveis: List<Movie>?
) : Parcelable
