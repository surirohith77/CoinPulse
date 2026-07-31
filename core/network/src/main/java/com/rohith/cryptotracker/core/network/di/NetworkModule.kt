package com.rohith.cryptotracker.core.network.di

import com.rohith.cryptotracker.core.network.api.CoinGeckoApi
import com.rohith.cryptotracker.core.network.datasource.NetworkDataSource
import com.rohith.cryptotracker.core.network.datasource.NetworkDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkDataSource(
        impl: NetworkDataSourceImpl
    ): NetworkDataSource

    companion object {

        private const val BASE_URL = "https://api.coingecko.com/api/v3/"
        private const val TIMEOUT_SECONDS = 15L

        @Provides
        @Singleton
        fun provideOkHttpClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun provideCoinGeckoApi(retrofit: Retrofit): CoinGeckoApi {
            return retrofit.create(CoinGeckoApi::class.java)
        }
    }
}
