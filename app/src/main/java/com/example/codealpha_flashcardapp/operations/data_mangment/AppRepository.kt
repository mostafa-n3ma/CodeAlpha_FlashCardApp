package com.example.codealpha_flashcardapp.operations.data_mangment

import androidx.lifecycle.LiveData
import javax.inject.Inject


class AppRepository @Inject constructor(
    private val userDao: UserDao,
    private val deckDao: DeckDao,
    private val flashCardDao: FlashCardDao
) {
    // User DAO operations
    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()
    suspend fun getUserById(userId: Int): User? = userDao.getUserById(userId)

    suspend fun getUserByEmail(email:String):User? = userDao.getUserByEmail(email)

    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    // Deck DAO operations
     fun getAllDecks(): LiveData<List<Deck>> = deckDao.getAllDecks()
    suspend fun getDeckById(deckId: Int): Deck? = deckDao.getDeckById(deckId)
    suspend fun insertDeck(deck: Deck) = deckDao.insertDeck(deck)
    suspend fun updateDeck(deck: Deck) = deckDao.updateDeck(deck)
    suspend fun deleteDeck(deck: Deck) = deckDao.deleteDeck(deck)

    // FlashCard DAO operations
     fun getAllFlashCards(): LiveData<List<FlashCard>> = flashCardDao.getAllFlashCards()
    suspend fun getFlashCardById(flashCardId: Int): FlashCard? =
        flashCardDao.getFlashCardById(flashCardId)

    fun getFlashCardByDeckId(deckId:Int):List<FlashCard> = flashCardDao.getFlashCardByDeckId(deckId)


    suspend fun insertFlashCard(flashCard: FlashCard) = flashCardDao.insertFlashCard(flashCard)
    suspend fun updateFlashCard(flashCard: FlashCard) = flashCardDao.updateFlashCard(flashCard)
    suspend fun deleteFlashCard(flashCard: FlashCard) = flashCardDao.deleteFlashCard(flashCard)
    fun getFlashCardsByDeckId(deckId: Int): LiveData<List<FlashCard>> {
        return flashCardDao.getFlashCardsByDeckId(deckId)
    }
}
