package com.example.pokemonapp.repository.pokemonApi.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import com.example.pokemonapp.repository.pokemonApi.model.PokemonListEntity
import retrofit2.http.Path


interface PokemonService {

    @GET("api/v2/pokemon/{pokemon_name}")
    fun getPokemonByName(@Path("pokemon_name") pokemonName: String):Call<PokemonEntity>

    @GET("api/v2/pokemon")
    fun getPokemonList(@Query("limit") limit: Int, @Query("offset") offset: Int):Call<PokemonListEntity>

}