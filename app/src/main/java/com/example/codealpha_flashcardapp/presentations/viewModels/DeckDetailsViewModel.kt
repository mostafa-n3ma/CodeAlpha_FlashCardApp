package com.example.codealpha_flashcardapp.presentations.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.codealpha_flashcardapp.operations.data_mangment.AppRepository
import com.example.codealpha_flashcardapp.operations.data_mangment.Deck
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckDetailsViewModel
@Inject
    constructor(private val repository: AppRepository)
    :ViewModel() {
        companion object{
            val TAG = "DeckDetailsViewModel"
        }

        private val _decK = MutableStateFlow<Deck>(Deck(0,0,"$$$$","$$$$$$$",1))
        val deck:StateFlow<Deck> = _decK


     val _flashCards = repository.getAllFlashCards().map {
         it.filter {
             it.Deck_id == _decK.value.id
         }
     }


    fun passDeckId(deckId: Int?) {
        if (deckId!=null){
            viewModelScope.launch(Dispatchers.Default) {
                _decK.update {
                    repository.getDeckById(deckId)?:_decK.value
                }
            }
        }
    }


    fun createNewCard(question:String,answer:String,hav_options:Boolean,op1:String,op2:String,op3:String){
        viewModelScope.launch(Dispatchers.Default) {
            repository.insertFlashCard(FlashCard(Deck_id = _decK.value.id, question = question, answer = answer, hasOptions = hav_options, options = listOf(op1,op2,op3)))
        }
    }

    fun deleteCard(card:FlashCard){
        viewModelScope.launch (Dispatchers.Default){
            repository.deleteFlashCard(card)
        }
    }



}