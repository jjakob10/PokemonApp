package com.example.pokemonapp.repository.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pokemonapp.repository.data.model.PokemonModel

@Dao
interface PokemonDAO {

    @Insert
    fun insert(p: PokemonModel): Long

    @Update
    fun update(p: PokemonModel): Int

    @Delete
    fun delete(p: PokemonModel)

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getById(id: Int): PokemonModel

    @Query("SELECT * FROM Pokemon WHERE name = :name")
    fun getByName(name: String): PokemonModel

    @Query("SELECT * FROM Pokemon")
    fun getAll(): List<PokemonModel>
}