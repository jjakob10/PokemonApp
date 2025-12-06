package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.repository.data.room.AppDatabase
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import java.util.Locale
import java.util.Locale.getDefault

class VisualizePokemonViewModel(application: Application) : AndroidViewModel(application) {

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

    fun getPokemonMale(): LiveData<Boolean> {
        return pokemonMale
    }

    fun getPokemonFemale(): LiveData<Boolean> {
        return pokemonFemale
    }

    fun getPokemonFavorite(): LiveData<Boolean> {
        return pokemonFavorite
    }


    fun getPokemonHp(): LiveData<Int> {
        return pokemonHp
    }

    fun getPokemonAttack(): LiveData<Int> {
        return pokemonAttack
    }

    fun getPokemonDefense(): LiveData<Int> {
        return pokemonDefense
    }

    fun getPokemonSpecialAttack(): LiveData<Int> {
        return pokemonSpecialAttack
    }

    fun getPokemonSpecialDefense(): LiveData<Int> {
        return pokemonSpecialDefense
    }

    fun getPokemonSpeed(): LiveData<Int> {
        return pokemonSpeed
    }


    fun getPokemonName(): LiveData<String> {
        return pokemonName
    }

    fun getPokemonImageUrl(): LiveData<String> {
        return pokemonImage
    }

    fun getPokemonAbilities(): LiveData<String> {
        return pokemonAbilities
    }


    fun toggleFavorite() {

        val db = AppDatabase.getDatabase(getApplication()).PokemonDAO()

        if (pokemonFavorite.value == true) {
            db.delete(pokemonModel)
        } else {
            db.insert(pokemonModel)
        }
        searchByName(pokemonModel.name)

    }

    fun searchByName(name: String) {
        val db = AppDatabase.getDatabase(getApplication()).PokemonDAO()
        try {
            val resp = db.getByName(name)
            if (resp == null) {
                pokemonFavorite.value = false
            } else {
                pokemonModel = resp
                pokemonFavorite.value = true
            }
        } catch (e: Exception) {
            pokemonFavorite.value = false
        }
    }


    fun fillContent(pokemon: PokemonEntity) {

        searchByName(pokemon.name.lowercase(getDefault()))

        if(pokemonFavorite.value == false) {
            pokemonModel = PokemonModel().apply {
                name = pokemon.name.lowercase(getDefault())
                maleImageUrl = pokemon.sprites.front_default  ?: ""
                femaleImageUrl = pokemon.sprites.front_female ?: ""

                // Abilities (3 fixos)
                ability1 = pokemon.abilities.getOrNull(0)?.ability?.name ?: ""
                ability2 = pokemon.abilities.getOrNull(1)?.ability?.name ?: ""
                ability3 = pokemon.abilities.getOrNull(2)?.ability?.name ?: ""

                // Stats (6 fixos)
                hp = pokemon.stats[0].base_stat
                attack = pokemon.stats[1].base_stat
                defense = pokemon.stats[2].base_stat
                specialAttack = pokemon.stats[3].base_stat
                specialDefense = pokemon.stats[4].base_stat
                speed = pokemon.stats[5].base_stat
            }

        }


        val img = if(pokemonModel.maleImageUrl != "") pokemonModel.maleImageUrl else pokemonModel.femaleImageUrl

        pokemonMale.value = pokemonModel.maleImageUrl != ""
        pokemonFemale.value = pokemonModel.femaleImageUrl != ""

        val abilities = listOfNotNull(
            pokemonModel.ability1.takeIf { it.isNotBlank() },
            pokemonModel.ability2.takeIf { it.isNotBlank() },
            pokemonModel.ability3.takeIf { it.isNotBlank() }
        ).joinToString("     ")

        pokemonAbilities.value = abilities
        pokemonImage.value = img
        pokemonName.value = pokemon.name
        pokemonHp.value = pokemon.stats[0].base_stat
        pokemonAttack.value = pokemon.stats[1].base_stat
        pokemonDefense.value = pokemon.stats[2].base_stat
        pokemonSpecialAttack.value = pokemon.stats[3].base_stat
        pokemonSpecialDefense.value = pokemon.stats[4].base_stat
        pokemonSpeed.value = pokemon.stats[5].base_stat

    }
}