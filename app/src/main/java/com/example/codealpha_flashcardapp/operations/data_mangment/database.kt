package com.example.codealpha_flashcardapp.operations.data_mangment

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Dao
interface UserDao {
    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserById(userId: Int): User?

    @Insert
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)
}

@Dao
interface DeckDao {
    @Query("SELECT * FROM decks")
    fun getAllDecks(): LiveData<List<Deck>>

    @Query("SELECT * FROM decks WHERE id = :deckId")
    fun getDeckById(deckId: Int): Deck?

    @Insert
    fun insertDeck(deck: Deck)

    @Update
    fun updateDeck(deck: Deck)

    @Delete
    fun deleteDeck(deck: Deck)
}

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM flashcards")
    fun getAllFlashCards(): List<FlashCard>

    @Query("SELECT * FROM flashcards WHERE id = :flashCardId")
    fun getFlashCardById(flashCardId: Int): FlashCard?

    @Insert
    fun insertFlashCard(flashCard: FlashCard)

    @Update
    fun updateFlashCard(flashCard: FlashCard)

    @Delete
    fun deleteFlashCard(flashCard: FlashCard)
    @Query("SELECT * FROM flashcards WHERE Deck_id = :deckId")
    fun getFlashCardsByDeckId(deckId: Int): LiveData<List<FlashCard>>
}



@Database(entities = [User::class, Deck::class, FlashCard::class], version = 1, exportSchema = false)
@TypeConverters(StringListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun deckDao(): DeckDao
    abstract fun flashCardDao(): FlashCardDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
