package com.example.pokemonapp.ui.viewHolder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FavoriteLineBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.ui.listener.OnPokemonListener

class ListFavoriteViewHolder (private val binding: FavoriteLineBinding, private val listener: OnPokemonListener) : RecyclerView.ViewHolder(binding.root) {


    fun bindVH(p: PokemonModel){

        binding.pokemonNameFavLine.text = p.name

        val img = if(p.maleImageUrl!="") p.maleImageUrl else p.femaleImageUrl

        binding.imagePokemonFavLine.load(img) {
            crossfade(true) // Optional: add a crossfade animation
            placeholder(R.drawable.ic_pokemon_placeholder) // Optional: show a placeholder
            error(R.drawable.ic_no_image) // Optional: show an error image
        }

        binding.imageFavFavLine.setOnClickListener({
            listener.onClick(p)
        })

    }

}