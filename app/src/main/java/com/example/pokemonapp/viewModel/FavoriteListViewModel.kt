package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.repository.data.room.AppDatabase
import com.example.pokemonapp.utils.Constants

class FavoriteListViewModel  (application: Application) : AndroidViewModel(application){

    var deletedPokemon: PokemonModel? = null
    private var pokemonList = MutableLiveData<List<PokemonModel>>()
    val listMsg = MutableLiveData<Int>()

    fun getPokemonList(): LiveData<List<PokemonModel>> {
        return pokemonList
    }

    fun getListMsg(): LiveData<Int> {
        return listMsg
    }

    fun cancelDelete(){
        val db = AppDatabase.getDatabase(getApplication()).PokemonDAO()

        if(deletedPokemon!=null){
            try {
                db.insert(deletedPokemon!!)
                val resp = db.getAll()
                pokemonList.value = resp
                listMsg.value = Constants.MSGS.DELETE_CANCEL
            } catch (e: Exception) {
                listMsg.value = Constants.MSGS.DELETE_CANCEL_FAIL
            }
        }
    }
    fun delete(p: PokemonModel) {
        val db = AppDatabase.getDatabase(getApplication()).PokemonDAO()
        try {
            db.delete(p)
            deletedPokemon = p
            val resp = db.getAll()
            pokemonList.value = resp
            listMsg.value = Constants.MSGS.DELETE_SUCCESS
        } catch (e: Exception) {
            listMsg.value = Constants.MSGS.DELETE_FAIL
        }
    }
    fun getAllPokemon() {
        val db = AppDatabase.getDatabase(getApplication()).PokemonDAO()
        try {
            val resp = db.getAll()
            if (resp == null) {
                listMsg.value = Constants.MSGS.NOT_FOUND
            } else {
                listMsg.value = Constants.MSGS.SUCCESS
                pokemonList.value = resp
            }
        } catch (e: Exception) {
            listMsg.value = Constants.MSGS.FAIL
        }
    }
}