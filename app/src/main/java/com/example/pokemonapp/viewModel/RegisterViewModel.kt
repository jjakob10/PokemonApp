package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val auth = FirebaseAuth.getInstance()
    private var register = MutableLiveData<Boolean>()
    private var errorMsg = MutableLiveData<Int>()

    fun getErrorMsg(): LiveData<Int> {
        return errorMsg
    }

    fun getRegister(): LiveData<Boolean> {
        return register
    }

    fun register(name: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener({
            if (it.isSuccessful) {
                register.value = true
            }
        }).addOnFailureListener({
            when (it) {
                is FirebaseAuthWeakPasswordException -> {
                    errorMsg.value = Constants.FIREBASE.WEAK_PASSWORD
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    errorMsg.value = Constants.FIREBASE.INVALID_CREDENTIAL
                }
                is FirebaseAuthUserCollisionException -> {
                    errorMsg.value = Constants.FIREBASE.USER_COLISION
                }
                else -> {
                    errorMsg.value = Constants.FIREBASE.FAIL
                }
            }
        })



    }

}