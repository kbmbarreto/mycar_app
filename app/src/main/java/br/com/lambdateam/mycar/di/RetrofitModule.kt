package br.com.lambdateam.mycar.di

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val PROPERTY_BASE_URL = "PROPERTY_BASE_URL"

private fun provideHttpClient(): OkHttpClient {
    val httpLogInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder()
        .addInterceptor(httpLogInterceptor)
        .build()
}

val retrofit = module {

    single {
        provideHttpClient()
    }

    single<Retrofit> {
        val baseUrl = getProperty<String>(PROPERTY_BASE_URL)
        Retrofit
            .Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(get())
            .build()
    }
}