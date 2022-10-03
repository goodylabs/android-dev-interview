package com.goodylabs.android.interview.di

import com.goodylabs.android.interview.data.api.CharacterService
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import com.goodylabs.android.interview.data.repositories.CharacterRepositoryImpl
import com.goodylabs.android.interview.data.util.DateJsonAdapter
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    companion object {

        @Singleton
        @Provides
        fun provideMoshi(): Moshi = Moshi.Builder()
            .add(DateJsonAdapter())
            .build()

        @Singleton
        @Provides
        fun provideHttpInterceptor() = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }

        @Singleton
        @Provides
        fun provideGoodyOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()

        @Singleton
        @Provides
        fun provideRetrofit(
            okHttpClient: OkHttpClient,
            moshi: Moshi
        ): Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        @Singleton
        @Provides
        fun provideCharacterService(retrofit: Retrofit): CharacterService = retrofit.create()
    }

    @Binds
    fun bindCharacterRepository(repository: CharacterRepositoryImpl): CharacterRepository
}
