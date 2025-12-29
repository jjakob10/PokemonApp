package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.repository.data.room.AppDatabase
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.VisualizePokemonViewModel.FavoriteOpStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FavoriteListViewModel(application: Application) : AndroidViewModel(application) {

    var deletedPokemon: PokemonModel? = null

    private val auth = FirebaseAuth.getInstance()

    private val db = FirebaseFirestore.getInstance()
    private var pokemonList = MutableLiveData<List<PokemonModel>>()
    val listMsg = MutableLiveData<Int>()

    fun getPokemonList(): LiveData<List<PokemonModel>> {
        return pokemonList
    }

    fun getListMsg(): LiveData<Int> {
        return listMsg
    }

    fun cancelDelete() {

        if (deletedPokemon != null) {
            try {
                val currUserId = auth.currentUser?.uid ?: return
                db.collection(currUserId).document(deletedPokemon!!.name)
                    .set(deletedPokemon!!)
                    .addOnSuccessListener {
                        db.collection(currUserId).get().addOnSuccessListener {
                            val resp: MutableList<PokemonModel> = mutableListOf()
                            it.forEach { document ->
                                val pokemon = document.toObject(PokemonModel::class.java)
                                resp.add(pokemon)
                            }
                            pokemonList.value = resp
                            listMsg.value = Constants.MSGS.DELETE_CANCEL
                        }.addOnFailureListener {
                            listMsg.value = Constants.MSGS.DELETE_CANCEL_FAIL
                        }
                    }
                    .addOnFailureListener {
                        listMsg.value = Constants.MSGS.DELETE_CANCEL_FAIL
                    }
            } catch (e: Exception) {
                listMsg.value = Constants.MSGS.DELETE_CANCEL_FAIL
            }
        }
    }

    fun delete(p: PokemonModel) {

        try {
            val currUserId = auth.currentUser?.uid ?: return
            db.collection(currUserId).document(p!!.name)
                .delete()
                .addOnSuccessListener {
                    deletedPokemon = p
                    db.collection(currUserId).get().addOnSuccessListener {
                        val resp: MutableList<PokemonModel> = mutableListOf()
                        it.forEach { document ->
                            val pokemon = document.toObject(PokemonModel::class.java)
                            resp.add(pokemon)
                        }
                        pokemonList.value = resp
                        listMsg.value = Constants.MSGS.DELETE_SUCCESS
                    }.addOnFailureListener {
                        listMsg.value = Constants.MSGS.DELETE_FAIL
                    }
                }
                .addOnFailureListener {
                    listMsg.value = Constants.MSGS.DELETE_FAIL

                }
        } catch (e: Exception) {
            listMsg.value = Constants.MSGS.DELETE_FAIL
        }

    }

    fun getAllPokemon() {
        try {
            val currUserId = auth.currentUser?.uid ?: return
            db.collection(currUserId).get().addOnSuccessListener {
                val resp: MutableList<PokemonModel> = mutableListOf()
                it.forEach { document ->
                    val pokemon = document.toObject(PokemonModel::class.java)
                    resp.add(pokemon)
                }
                pokemonList.value = resp
                listMsg.value = Constants.MSGS.SUCCESS
            }.addOnFailureListener {
                listMsg.value = Constants.MSGS.NOT_FOUND
            }

        } catch (e: Exception) {
            listMsg.value = Constants.MSGS.FAIL
        }
    }
}