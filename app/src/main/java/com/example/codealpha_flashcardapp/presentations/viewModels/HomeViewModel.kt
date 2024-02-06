package com.example.codealpha_flashcardapp.presentations.viewModels

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codealpha_flashcardapp.operations.data_mangment.AppRepository
import com.example.codealpha_flashcardapp.operations.data_mangment.Deck
import com.example.codealpha_flashcardapp.operations.data_mangment.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel
@Inject
constructor(private val repository: AppRepository) : ViewModel() {


    private val _activeUserName = MutableStateFlow<String>("")
    val activeUserName:StateFlow<String> = _activeUserName

    private lateinit var currentUser:User

     val _decksList=repository.getAllDecks()




    fun getUserName(email: String){
        viewModelScope.launch(Dispatchers.Default) {
            var username = ""
            val user: User? = repository.getUserByEmail(email)
            if (user != null){
                username = user.userName
                currentUser= user
            }
            _activeUserName.update { username }
        }
    }

    fun deleteDeck(deck: Deck){
        viewModelScope.launch(Dispatchers.Default) {
            repository.deleteDeck(deck)
        }
    }


    fun addNewDeck(title:String, description:String){
        viewModelScope.launch(Dispatchers.Default){
            repository.insertDeck(Deck(
                title = title,
                description = description,
                user_id = currentUser.id,
                bg_color = Random.nextInt(0, 7)
            ))
        }
    }

}
