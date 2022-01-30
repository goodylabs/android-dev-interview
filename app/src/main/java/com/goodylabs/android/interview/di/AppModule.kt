package com.goodylabs.android.interview.di

import com.goodylabs.android.interview.ui.characterlist.CharacterAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideCharacterAdapter(): CharacterAdapter = CharacterAdapter()
}
