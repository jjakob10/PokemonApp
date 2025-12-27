package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

class LoginViewModel  (application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()

    private var login = MutableLiveData<Boolean>()
    private var errorMsg = MutableLiveData<Int>()

    fun getLogin(): LiveData<Boolean> {
        return login
    }

    fun getErrorMsg(): LiveData<Int> {
        return errorMsg
    }

    fun login(email: String, pass: String) {

        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener({
            if (it.isSuccessful){
                login.value = true
            }
        }).addOnFailureListener({
            when (it) {
                is FirebaseAuthInvalidUserException -> {
                    errorMsg.value = Constants.FIREBASE.INVALID_USER
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    errorMsg.value = Constants.FIREBASE.WRONG_PASSWORD
                }
                else -> {
                    errorMsg.value = Constants.FIREBASE.FAIL
                }
            }
        })

    }

}