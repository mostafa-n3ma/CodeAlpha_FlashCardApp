package com.example.codealpha_flashcardapp.operations.di

import com.example.codealpha_flashcardapp.operations.data_mangment.AppRepository
import com.example.codealpha_flashcardapp.operations.data_mangment.DeckDao
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCardDao
import com.example.codealpha_flashcardapp.operations.data_mangment.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(userDao: UserDao,deckDao: DeckDao,flashCardDao: FlashCardDao):AppRepository{
        return AppRepository(userDao, deckDao, flashCardDao)
    }

}