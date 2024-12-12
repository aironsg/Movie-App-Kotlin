package dev.airon.movieapp.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.airon.movieapp.data.datasource.remote.api.ServiceAPI
import dev.airon.movieapp.data.datasource.remote.network.ServiceProvider

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun providesServiceProvider() = ServiceProvider()

    @Provides
    fun provideServiceAPI(
        serviceProvider: ServiceProvider
    ) : ServiceAPI {
        return serviceProvider.createService(ServiceAPI::class.java)

    }
}