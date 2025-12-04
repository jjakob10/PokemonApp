package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MainViewModel (application: Application) : AndroidViewModel(application) {

//    val dogDb = DogAppDatabase.getDatabase(getApplication()).CuriosityDAO()
//    val catDb = CatAppDatabase.getDatabase(getApplication()).CuriosityDAO()


    private var selected = MutableLiveData(0)


//    val apiCat = CatClientRetrofit.createService(CatCuriosityService::class.java)
//    val apiDog = DogClientRetrofit.createService(DogCuriosityService::class.java)

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