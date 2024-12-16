package dev.airon.movieapp.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(

    val id: Int,
    val name: String?

): Parcelable
