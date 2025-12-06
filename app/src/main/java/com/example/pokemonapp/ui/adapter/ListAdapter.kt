package com.example.pokemonapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonapp.databinding.FavoriteLineBinding
import com.example.pokemonapp.databinding.PokemonItemBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.ui.listener.OnPokemonListener
import com.example.pokemonapp.ui.viewHolder.ListFavoriteViewHolder
import com.example.pokemonapp.ui.viewHolder.ListViewHolder

class ListAdapter : RecyclerView.Adapter<ListViewHolder>(){
    private var pokemonList: List<PokemonModel> = listOf()
    private lateinit var listener: OnPokemonListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val item = PokemonItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)

        return ListViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindVH(pokemonList[position])
    }


    override fun getItemCount(): Int {
        return pokemonList.count()
    }


    fun updatePokemonList(list: List<PokemonModel>) {
        pokemonList = list
        notifyDataSetChanged()
    }

    fun setListener(productListener: OnPokemonListener) {
        listener = productListener
    }
}