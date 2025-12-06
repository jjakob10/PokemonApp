package com.example.pokemonapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentFavoriteListBinding
import com.example.pokemonapp.databinding.FragmentSearchBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.ui.adapter.ListFavoriteAdapter
import com.example.pokemonapp.ui.listener.OnPokemonListener
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.FavoriteListViewModel
import com.example.pokemonapp.viewModel.SearchViewModel

class FavoriteListFragment : Fragment(R.layout.fragment_search) {


    private lateinit var favoriteListVM: FavoriteListViewModel
    private var _binding: FragmentFavoriteListBinding? = null

    private val adapter = ListFavoriteAdapter()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentFavoriteListBinding.inflate(inflater, container, false)

        // layout
        binding.recyclerListFavorites.layoutManager = LinearLayoutManager(context)

        // Adapter
        binding.recyclerListFavorites.adapter = adapter


        favoriteListVM = ViewModelProvider(this).get(FavoriteListViewModel::class.java)

        val listener = object : OnPokemonListener {
            override fun onClick(p: PokemonModel) {
                favoriteListVM.delete(p)

            }
        }
        adapter.setListener(listener)


        setObserver()
        favoriteListVM.getAllPokemon()

        return binding.root
    }

    fun setObserver() {
        favoriteListVM.getListMsg().observe(viewLifecycleOwner, Observer {
            if (it == Constants.MSGS.SUCCESS){
                Toast.makeText(context, R.string.favorite_found, Toast.LENGTH_SHORT).show()
            } else if (it == Constants.MSGS.NOT_FOUND){
                Toast.makeText(context, R.string.not_found, Toast.LENGTH_SHORT).show()
            }else if(it == Constants.MSGS.DELETE_FAIL){
                Toast.makeText(context, R.string.delete_fail, Toast.LENGTH_SHORT).show()
            }else if(it == Constants.MSGS.DELETE_SUCCESS){
                Toast.makeText(context, R.string.delete_sucess, Toast.LENGTH_SHORT).show()
            }
        })

        favoriteListVM.getPokemonList().observe(viewLifecycleOwner, Observer {
            adapter.updatePokemonList(it)
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}