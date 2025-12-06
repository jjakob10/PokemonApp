package com.example.pokemonapp.ui.listener

import com.example.pokemonapp.repository.data.model.PokemonModel

interface OnPokemonListener {
    fun onClick(p: PokemonModel)
}