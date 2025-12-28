package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Locale

class VisualizePokemonViewModel(application: Application) : AndroidViewModel(application) {

    sealed class FavoriteOpStatus {
        object IDLE : FavoriteOpStatus()
        object ADD_SUCCESS : FavoriteOpStatus()
        object DELETE_SUCCESS : FavoriteOpStatus()
        object OP_ERROR : FavoriteOpStatus()
    }

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private val _favoriteOpStatus = MutableLiveData<FavoriteOpStatus>(FavoriteOpStatus.IDLE)
    val favoriteOpStatus: LiveData<FavoriteOpStatus> = _favoriteOpStatus

    val pokemonMale = MutableLiveData<Boolean>()
    val pokemonFemale = MutableLiveData<Boolean>()
    val pokemonAbilities = MutableLiveData<String>()
    val pokemonImage = MutableLiveData<String>()
    val pokemonName = MutableLiveData<String>()
    val pokemonHp = MutableLiveData<Int>()
    val pokemonAttack = MutableLiveData<Int>()
    val pokemonDefense = MutableLiveData<Int>()
    val pokemonSpecialAttack = MutableLiveData<Int>()
    val pokemonSpecialDefense = MutableLiveData<Int>()
    val pokemonSpeed = MutableLiveData<Int>()
    val pokemonFavorite = MutableLiveData<Boolean>()
    var pokemonModel = PokemonModel()

    fun getPokemonMale(): LiveData<Boolean> = pokemonMale
    fun getPokemonFemale(): LiveData<Boolean> = pokemonFemale
    fun getPokemonFavorite(): LiveData<Boolean> = pokemonFavorite
    fun getPokemonHp(): LiveData<Int> = pokemonHp
    fun getPokemonAttack(): LiveData<Int> = pokemonAttack
    fun getPokemonDefense(): LiveData<Int> = pokemonDefense
    fun getPokemonSpecialAttack(): LiveData<Int> = pokemonSpecialAttack
    fun getPokemonSpecialDefense(): LiveData<Int> = pokemonSpecialDefense
    fun getPokemonSpeed(): LiveData<Int> = pokemonSpeed
    fun getPokemonName(): LiveData<String> = pokemonName
    fun getPokemonImageUrl(): LiveData<String> = pokemonImage
    fun getPokemonAbilities(): LiveData<String> = pokemonAbilities

    fun toggleFavorite() {
        if (pokemonFavorite.value == true) {
            removeFavorite()
        } else {
            addFavorite()
        }
    }

    fun addFavorite() {
        val currUserId = auth.currentUser?.uid ?: return
        pokemonFavorite.value = true
        db.collection(currUserId).document(pokemonModel.name)
            .set(pokemonModel)
            .addOnSuccessListener {
                _favoriteOpStatus.value = FavoriteOpStatus.ADD_SUCCESS
            }
            .addOnFailureListener {
                pokemonFavorite.value = false // Revert optimistic update
                _favoriteOpStatus.value = FavoriteOpStatus.OP_ERROR
            }
    }

    fun removeFavorite() {
        val currUserId = auth.currentUser?.uid ?: return
        pokemonFavorite.value = false
        db.collection(currUserId).document(pokemonModel.name)
            .delete()
            .addOnSuccessListener {
                _favoriteOpStatus.value = FavoriteOpStatus.DELETE_SUCCESS
            }
            .addOnFailureListener {
                pokemonFavorite.value = true // Revert optimistic update
                _favoriteOpStatus.value = FavoriteOpStatus.OP_ERROR
            }
    }

    fun onSnackbarShown() {
        _favoriteOpStatus.value = FavoriteOpStatus.IDLE
    }

    fun searchByName(name: String) {
        try {
            val currUserId = auth.currentUser?.uid
            db.collection(currUserId!!).document(name)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val resp = document.toObject(PokemonModel::class.java)
                        if (resp != null) {
                            pokemonModel = resp
                            pokemonFavorite.value = true
                        } else {
                            pokemonFavorite.value = false
                        }
                    } else {
                        pokemonFavorite.value = false
                    }
                }
                .addOnFailureListener {
                    pokemonFavorite.value = false
                }
        } catch (e: Exception) {
            pokemonFavorite.value = false
        }
    }


    fun fillContent(pokemon: PokemonModel) {
        searchByName(pokemon.name.lowercase(Locale.getDefault()))
        pokemonModel = pokemon
        val img = if (pokemonModel.maleImageUrl.isNotEmpty()) pokemonModel.maleImageUrl else pokemonModel.femaleImageUrl
        pokemonMale.value = pokemonModel.maleImageUrl.isNotEmpty()
        pokemonFemale.value = pokemonModel.femaleImageUrl.isNotEmpty()
        val abilities = listOfNotNull(
            pokemonModel.ability1.takeIf { it.isNotBlank() },
            pokemonModel.ability2.takeIf { it.isNotBlank() },
            pokemonModel.ability3.takeIf { it.isNotBlank() }
        ).joinToString("     ")
        pokemonAbilities.value = abilities
        pokemonImage.value = img
        pokemonName.value = pokemonModel.name
        pokemonHp.value = pokemonModel.hp
        pokemonAttack.value = pokemonModel.attack
        pokemonDefense.value = pokemonModel.defense
        pokemonSpecialAttack.value = pokemonModel.specialAttack
        pokemonSpecialDefense.value = pokemonModel.specialDefense
        pokemonSpeed.value = pokemonModel.speed
    }
}
