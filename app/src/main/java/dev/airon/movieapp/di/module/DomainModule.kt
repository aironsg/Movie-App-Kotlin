package dev.airon.movieapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.airon.movieapp.data.repository.auth.FirebaseAuthenticationImpl
import dev.airon.movieapp.data.repository.movie.MovieRepositoryImpl
import dev.airon.movieapp.domain.repository.auth.FirebaseAuthentication
import dev.airon.movieapp.domain.repository.movie.MovieRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticationImpl(firebaseAuthImpl: FirebaseAuthenticationImpl) : FirebaseAuthentication

    @Binds
    abstract fun bindsMovieRepositoryImpl(movieRepositoryImpl: MovieRepositoryImpl) : MovieRepository

}