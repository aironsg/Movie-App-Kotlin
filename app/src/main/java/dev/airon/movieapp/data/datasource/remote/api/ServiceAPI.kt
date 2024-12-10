package dev.airon.movieapp.data.datasource.remote.api

import dev.airon.movieapp.data.model.BasePaginationRemote
import dev.airon.movieapp.data.model.GenresResponse
import dev.airon.movieapp.data.model.dto.MovieDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ServiceAPI {

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?
    ): GenresResponse


    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String?,
        @Query("with_genres") genreId: String?
    ): BasePaginationRemote<List<MovieDTO>>


}