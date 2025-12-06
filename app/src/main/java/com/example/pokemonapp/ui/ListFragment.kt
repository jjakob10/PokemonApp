package com.example.pokemonapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentListBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.ui.adapter.ListAdapter
import com.example.pokemonapp.ui.listener.OnPokemonListener
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.ListViewModel

class ListFragment : Fragment(R.layout.fragment_search), View.OnClickListener {


    private lateinit var listVM: ListViewModel
    private var _binding: FragmentListBinding? = null
    private val adapter = ListAdapter()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentListBinding.inflate(inflater, container, false)

        // layout
        binding.recyclerListGrid.layoutManager = GridLayoutManager(context, 3)

        // Adapter
        binding.recyclerListGrid.adapter = adapter


        listVM = ViewModelProvider(this).get(ListViewModel::class.java)

        val listener = object : OnPokemonListener {
            override fun onClick(p: PokemonModel) {

//                val intent = Intent(context, VisualizePokemonActivity::class.java)
//
//                val bundle = bundleOf(
//                    "pokemon" to p
//                )
//
//                intent.putExtras(bundle)
//                startActivity(intent)

            }
        }
        adapter.setListener(listener)

        binding.buttonLoadMore.setOnClickListener(this)
        setObserver()
        listVM.loadMore()

        return binding.root
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonLoadMore -> {
                listVM.loadMore()
            }
        }
    }
    fun setObserver() {
        listVM.getListMsg().observe(viewLifecycleOwner, Observer {
            if (it == Constants.MSGS.SUCCESS){
                Toast.makeText(context, R.string.load_sucess, Toast.LENGTH_SHORT).show()
            } else if (it == Constants.MSGS.FAIL){
                Toast.makeText(context, R.string.load_fail, Toast.LENGTH_SHORT).show()
            }
        })

        listVM.getPokemonList().observe(viewLifecycleOwner, Observer {
            adapter.updatePokemonList(it)
        })
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}