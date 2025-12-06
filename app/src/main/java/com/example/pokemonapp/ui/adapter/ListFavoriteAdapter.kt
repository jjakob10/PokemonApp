package com.example.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.databinding.FavoriteLineBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.ui.listener.OnPokemonListener
import com.example.pokemonapp.ui.viewHolder.ListFavoriteViewHolder

class ListFavoriteAdapter : RecyclerView.Adapter<ListFavoriteViewHolder>(){
    private var prodList: List<PokemonModel> = listOf()
    private lateinit var listener: OnPokemonListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFavoriteViewHolder {
        val item = FavoriteLineBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)

        return ListFavoriteViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: ListFavoriteViewHolder, position: Int) {
        holder.bindVH(prodList[position])
    }


    override fun getItemCount(): Int {
        return prodList.count()
    }


    fun updatePokemonList(list: List<PokemonModel>) {
        prodList = list
        notifyDataSetChanged()
    }

    fun setListener(productListener: OnPokemonListener) {
        listener = productListener
    }
}