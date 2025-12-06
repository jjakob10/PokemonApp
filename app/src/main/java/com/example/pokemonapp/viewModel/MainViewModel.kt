package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private var selected = MutableLiveData(0)

    init {
        selectSearch()
    }

    fun getSelected(): LiveData<Int> {
        return selected
    }


    fun selectSearch() {
        selected.value = 0
    }

    fun selectFavorite() {
        selected.value = 1
    }

    fun selectList() {
        selected.value = 2
    }


}