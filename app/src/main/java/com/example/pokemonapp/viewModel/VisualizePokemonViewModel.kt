package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity

class VisualizePokemonViewModel (application: Application) : AndroidViewModel(application){


    val pokemonImage = MutableLiveData<String>()

    val pokemonName = MutableLiveData<String>()

    val pokemonHp = MutableLiveData<Int>()

    val pokemonAttack = MutableLiveData<Int>()

    val pokemonDefense = MutableLiveData<Int>()

    val pokemonSpecialAttack = MutableLiveData<Int>()

    val pokemonSpecialDefense = MutableLiveData<Int>()

    val pokemonSpeed = MutableLiveData<Int>()

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


    fun fillContent(pokemon: PokemonEntity){
        pokemonImage.value = pokemon.sprites.front_default
        pokemonName.value = pokemon.name
        pokemonHp.value = pokemon.stats[0].base_stat
        pokemonAttack.value = pokemon.stats[1].base_stat
        pokemonDefense.value = pokemon.stats[2].base_stat
        pokemonSpecialAttack.value = pokemon.stats[3].base_stat
        pokemonSpecialDefense.value = pokemon.stats[4].base_stat
        pokemonSpeed.value = pokemon.stats[5].base_stat

    }
}