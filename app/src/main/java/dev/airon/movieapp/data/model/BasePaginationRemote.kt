package dev.airon.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class BasePaginationRemote<out T>(
    @SerializedName("page")
    private val page : Int?,

    @SerializedName("results")
    private val results : T?,

    @SerializedName("total_results")
    private val totalResults : Int?,

    @SerializedName("total_pages")
    private val totalPages : Int?


)
