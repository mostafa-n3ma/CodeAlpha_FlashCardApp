package com.example.codealpha_flashcardapp.presentations.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.codealpha_flashcardapp.operations.data_mangment.AppRepository
import com.example.codealpha_flashcardapp.operations.data_mangment.User
import com.example.codealpha_flashcardapp.operations.validateEmail
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log


@HiltViewModel
class AuthViewModel
@Inject constructor(
    val repository: AppRepository
) : ViewModel() {
    companion object{
        val TAG = "AuthViewModel"
    }

     var  firebaseAuth:FirebaseAuth




    private val _announceMessage = MutableStateFlow<String>("")
    val announceMessage:StateFlow<String> = _announceMessage

     fun announceMessage(msg:String){
        _announceMessage.update {
            Log.d("announcment test  announceMessage()"  , "update to msg : $msg")
            msg
        }
    }



    private val _user = MutableStateFlow<FirebaseUser?>(null)
    val user:StateFlow<FirebaseUser?> = _user






    private val _isUserSignedIn = MutableStateFlow<Boolean>(false)
    val isUserSignedIn:StateFlow<Boolean> = _isUserSignedIn

    fun getAuthInstance(instance:FirebaseAuth){
        firebaseAuth = instance
    }

    init {
        firebaseAuth = Firebase.auth
        _user.update { firebaseAuth.currentUser }
        checkAndUpdateUserStatus()
    }

     fun checkAndUpdateUserStatus(){
        _user.update { firebaseAuth.currentUser }
    }




    fun signOut(){
        firebaseAuth.signOut()
        checkAndUpdateUserStatus()
    }

    fun validateEmailAndPassword(email: String, password: String) :Boolean{
        Log.d("announcment test  "  , "validateEmailAndPassword() email : $email // password: $password  ")
        if (email.trim().isEmpty()){
            announceMessage( "email is empty")
            Log.d("announcment test", ":validateEmailAndPassword() email is empty")
            return false
        }
        if (password.trim().isEmpty()){
            announceMessage ("password is empty")
            Log.d("announcment test", "validateEmailAndPassword: password is empty")

            return false
        }
        if (!validateEmail(email)){
            announceMessage("email is not valid!")
            Log.d("announcment test", "validateEmailAndPassword: email is not valid!")
            return false
        }

        if (password.length < 6 ){
            announceMessage("password should not be less then 6 characters ")
            Log.d("announcment test", "validateEmailAndPassword: password should not be less then 6 characters")
            return false
        }

        Log.d("announcment test", "validateEmailAndPassword: nothing is wroong returning true")

        return true

    }


    //sign upScreen
    fun createUser(name:String, email:String,password:String){
        if (validateEmailAndPassword(email, password)){
            firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    if (it !=null){
                        checkAndUpdateUserStatus()
                        Log.d(TAG, "createUserWithEmailAndPassword: success creating account ")
                        viewModelScope.launch (Dispatchers.Default){
                            repository.insertUser(User(userName = name, email = email, password = ""))
                        }
                    }
                }
                .addOnFailureListener {
                    _isUserSignedIn.update { false }
                    Log.d(TAG, "createUserWithEmailAndPassword: failing creating account ///e: ${it.message}")
                    announceMessage("${it.message}")
                }
        }

    }




    // sign in Screen
    fun signIn(email: String, password: String){
        if (validateEmailAndPassword(email, password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    checkAndUpdateUserStatus()
                    Log.d(TAG, "signIn: Successfully signed in ")
                }
                .addOnFailureListener {
                    Log.d(TAG, "signIn: Fail ${it.message}")
                    announceMessage("${it.message}")
                }

        }


    }






}