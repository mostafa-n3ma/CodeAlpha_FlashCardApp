package com.example.codealpha_flashcardapp.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codealpha_flashcardapp.operations.data_mangment.AppRepository
import com.example.codealpha_flashcardapp.operations.data_mangment.Deck
import com.example.codealpha_flashcardapp.operations.data_mangment.FlashCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel
@Inject
    constructor(private val repository: AppRepository):ViewModel() {

    private val _decK = MutableStateFlow<Deck>(Deck(0,0,"$$$$","$$$$$$$",1))
    val deck: StateFlow<Deck> = _decK

    private val _quizCards=MutableStateFlow<List<FlashCard>>(emptyList())
    val quizCards:StateFlow<List<FlashCard>> = _quizCards
    fun passDeckId(deckId: Int?) {
        if (deckId!=null){
            viewModelScope.launch(Dispatchers.Default) {
                _decK.update {
                    repository.getDeckById(deckId)?:_decK.value
                }
                _quizCards.update {
                    repository.getFlashCardByDeckId(deckId)
                }
            }
        }
    }

    private val _cardAnimationState= MutableStateFlow<Boolean>(true)
    val cardAnimationState: StateFlow<Boolean> =_cardAnimationState

    private val _onScreenCard=MutableStateFlow<FlashCard>(FlashCard(Deck_id = 0, question = "", answer = "", hasOptions = false, options = emptyList()))
    val onScreenCard:StateFlow<FlashCard> = _onScreenCard

    private val _score = MutableStateFlow<Int>(0)
    val score: StateFlow<Int> =_score

    private var currentCardIndex:Int=0

    //call this in the screen when the quizCards list isNotEmpty
    fun onQuizCardsReady(){
        if (_quizCards.value.isNotEmpty()){
            _onScreenCard.value = _quizCards.value[currentCardIndex]
        }
    }

    private val _finalScoreVisibility = MutableStateFlow<Boolean>(false)
    val finalScoreVisibility:StateFlow<Boolean> = _finalScoreVisibility

    private fun answerIsCorrect(answer:String, card:FlashCard):Boolean{
        return answer == card.answer
    }




    // call in onClick of the submitButton in the quiz card
    fun passCard(answer: String,card: FlashCard){
        viewModelScope.launch (Dispatchers.IO){
            //check the answer is Correct and update the score when true
            if (answerIsCorrect(answer, card)){_score.update { _score.value+1 }}

            //check the currentCardIndex if not more then the list size which is not the last item in the list
            if (currentCardIndex !=_quizCards.value.lastIndex){
                currentCardIndex++
                _cardAnimationState.update { false }
                _onScreenCard.update { _quizCards.value[currentCardIndex] }
                delay(1000)
                _cardAnimationState.update { true }
            }else{
                _cardAnimationState.update { false }
                _finalScoreVisibility.update { true }
            }
        }
    }






}