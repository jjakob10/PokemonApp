package com.example.pokemonapp.repository.mapper

import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import java.util.Locale

fun toPokemonModel(pokemonEntity: PokemonEntity): PokemonModel {
    return PokemonModel().apply {
        name = pokemonEntity.name.lowercase(Locale.getDefault())
        maleImageUrl = pokemonEntity.sprites.front_default ?: ""
        femaleImageUrl = pokemonEntity.sprites.front_female ?: ""

        ability1 = pokemonEntity.abilities.getOrNull(0)?.ability?.name ?: ""
        ability2 = pokemonEntity.abilities.getOrNull(1)?.ability?.name ?: ""
        ability3 = pokemonEntity.abilities.getOrNull(2)?.ability?.name ?: ""

        hp = pokemonEntity.stats.getOrNull(0)?.base_stat ?: 0
        attack = pokemonEntity.stats.getOrNull(1)?.base_stat ?: 0
        defense = pokemonEntity.stats.getOrNull(2)?.base_stat ?: 0
        specialAttack = pokemonEntity.stats.getOrNull(3)?.base_stat ?: 0
        specialDefense = pokemonEntity.stats.getOrNull(4)?.base_stat ?: 0
        speed = pokemonEntity.stats.getOrNull(5)?.base_stat ?: 0
    }
}
