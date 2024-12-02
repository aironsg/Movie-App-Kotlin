package dev.airon.movieapp.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.airon.movieapp.data.repository.auth.FirebaseAuthenticationImpl
import dev.airon.movieapp.domain.repository.auth.FirebaseAuthentication

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindsFirebaseAuthenticationImpl(firebaseAuthImpl: FirebaseAuthenticationImpl) : FirebaseAuthentication

}