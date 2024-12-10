package dev.airon.movieapp.data.model

import com.google.gson.annotations.SerializedName
import dev.airon.movieapp.data.model.dto.GenreDTO

data class GenresResponse(
    @SerializedName("genres")
    val genres: List<GenreDTO>?

)
