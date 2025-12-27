package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LoginViewModel  (application: Application) : AndroidViewModel(application) {

    private var login = MutableLiveData<Boolean>()

    fun getLogin(): LiveData<Boolean> {
        return login
    }

    fun login() {
        login.value = true
    }

}