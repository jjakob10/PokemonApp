package com.example.pokemonapp.repository.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="Pokemon", indices = [Index(value = ["name"], unique = true)])
class PokemonModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id: Int = 0

    @ColumnInfo(name="name")
    var name: String = ""

    @ColumnInfo(name = "male_image_url")
    var maleImageUrl: String = ""

    @ColumnInfo(name = "female_image_url")
    var femaleImageUrl: String = ""

    @ColumnInfo(name = "ability_1")
    var ability1: String = ""

    @ColumnInfo(name = "ability_2")
    var ability2: String = ""

    @ColumnInfo(name = "ability_3")
    var ability3: String = ""

    @ColumnInfo(name = "hp")
    var hp: Int = 0

    @ColumnInfo(name = "attack")
    var attack: Int = 0

    @ColumnInfo(name = "defense")
    var defense: Int = 0

    @ColumnInfo(name = "special_attack")
    var specialAttack: Int = 0

    @ColumnInfo(name = "special_defense")
    var specialDefense: Int = 0

    @ColumnInfo(name = "speed")
    var speed: Int = 0



}