package com.example.pokemonapp.ui

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentFavoriteListBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.ui.adapter.ListFavoriteAdapter
import com.example.pokemonapp.ui.listener.OnPokemonListener
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.FavoriteListViewModel
import com.google.android.material.snackbar.Snackbar

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

                AlertDialog.Builder(context)
                    .setTitle("Remover pokemon favorito")
                    .setMessage("Tem certeza que deseja remover ${p.name} dos favoritos?")
                    .setPositiveButton("Sim", { dialog, bt ->
                        favoriteListVM.delete(p)
                    })
                    .setNegativeButton("Não", { dialog, bt ->
                    })
                    .create()
                    .show()




            }
        }
        adapter.setListener(listener)


        setObserver()
        favoriteListVM.getAllPokemon()

        return binding.root
    }

    fun setObserver() {
        favoriteListVM.getListMsg().observe(viewLifecycleOwner, Observer { it ->
            if (it == Constants.MSGS.SUCCESS){
                makeSnack(R.string.favorite_found)
            } else if (it == Constants.MSGS.NOT_FOUND){
                makeSnack(R.string.not_found)
            }else if(it == Constants.MSGS.DELETE_FAIL){
                makeSnack(R.string.delete_fail)
            }else if(it == Constants.MSGS.DELETE_SUCCESS){
                val snack = Snackbar.make(binding.recyclerListFavorites, R.string.delete_sucess, Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(Color.DKGRAY)
                snack.setTextColor(Color.WHITE)
                snack.setTextMaxLines(1)

                snack.setAction(R.string.undo, View.OnClickListener {
                    favoriteListVM.cancelDelete()
                })

                snack.show()

            }else if(it == Constants.MSGS.DELETE_CANCEL){
                makeSnack(R.string.delete_cancel)
            }else if(it == Constants.MSGS.DELETE_CANCEL_FAIL){
                makeSnack(R.string.delete_cancel_fail)
            }
        })

        favoriteListVM.getPokemonList().observe(viewLifecycleOwner, Observer {
            adapter.updatePokemonList(it)
        })
    }

    private fun makeSnack(msg: Int){
        val snack = Snackbar.make(binding.recyclerListFavorites, msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(Color.DKGRAY)
        snack.setTextColor(Color.WHITE)
        snack.setTextMaxLines(1)
        snack.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}