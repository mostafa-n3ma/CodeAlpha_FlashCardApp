package com.example.codealpha_flashcardapp.operations.di

import android.content.Context
import androidx.room.Room
import com.example.codealpha_flashcardapp.operations.data_mangment.AppDatabase
import com.example.codealpha_flashcardapp.operations.data_mangment.DeckDao
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCardDao
import com.example.codealpha_flashcardapp.operations.data_mangment.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):AppDatabase{
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "App Database"
        ).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideUserDao(database: AppDatabase):UserDao{
        return database.userDao()
    }


    @Singleton
    @Provides
    fun provideDecksDao(database: AppDatabase):DeckDao{
        return database.deckDao()
    }

    @Singleton
    @Provides
    fun provideFlashCardsDao(database: AppDatabase):FlashCardDao{
        return database.flashCardDao()
    }

}