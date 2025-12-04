package com.example.pokemonapp.repository.pokemonApi.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokemonEntity : Serializable {
    @SerializedName("name")
    var name: String = ""

    @SerializedName("abilities")
    var abilities: List<AbilityItem> = emptyList()

    @SerializedName("stats")
    var stats: List<StatItem> = emptyList()

    @SerializedName("sprites")
    var sprites: Sprites = Sprites()
}

class Sprites : Serializable {

    @SerializedName("front_default")
    var front_default: String = ""

    @SerializedName("front_female")
    var back_default: String = ""
}

class AbilityItem : Serializable {

    @SerializedName("ability")
    var ability: Ability = Ability()

}

class Ability : Serializable {

    @SerializedName("name")
    var name: String = ""
}

class StatItem : Serializable {

    @SerializedName("base_stat")
    var base_stat: Int = 0

    @SerializedName("stat")
    var stat: Stat = Stat()
}

class Stat : Serializable {

    @SerializedName("name")
    var name: String = ""
}

