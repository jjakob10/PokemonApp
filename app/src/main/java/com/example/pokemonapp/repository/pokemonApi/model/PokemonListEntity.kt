package com.example.pokemonapp.repository.pokemonApi.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokemonListEntity : Serializable {

    @SerializedName("next")
    var next: String = ""

    @SerializedName("results")
    var results: List<ResultItem> = emptyList()
}

class ResultItem : Serializable {

    @SerializedName("name")
    var name: String = ""

    @SerializedName("url")
    var url: String = ""
}