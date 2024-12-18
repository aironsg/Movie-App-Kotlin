package dev.airon.movieapp.data.datasource.remote.network

import dev.airon.movieapp.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceProvider {

    //responsavel por fazer a chamada a API
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    //responsavel por fazer a configuração dos dados da API
        private val retrofit  = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()



    //função responsavel por criar o serviço
    fun <API> createService(apiClass: Class<API>): API = retrofit.create(apiClass)

}