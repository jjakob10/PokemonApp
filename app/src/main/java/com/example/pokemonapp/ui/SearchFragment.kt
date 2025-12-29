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
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.FragmentSearchBinding
import com.example.pokemonapp.ui.LoginActivity
import com.example.pokemonapp.utils.Constants
import com.example.pokemonapp.viewModel.SearchViewModel


class SearchFragment : Fragment(R.layout.fragment_search), View.OnClickListener {

    private lateinit var searchVM: SearchViewModel
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)


        searchVM = ViewModelProvider(this).get(SearchViewModel::class.java)
        setObserver()



        binding.buttonPesquisar.setOnClickListener(this)
        binding.buttonLogout.setOnClickListener(this)



        return binding.root
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonPesquisar -> {
                val pokemonName = binding.EditTextPokemonName.text.toString()
                if (pokemonName == "") {
                    Toast.makeText(context, R.string.valid_name, Toast.LENGTH_SHORT).show()
                } else {
                    searchVM.search(binding.EditTextPokemonName.text.toString())
                }
            }
            R.id.buttonLogout -> {
                searchVM.logout()
            }
        }
    }

    private fun setObserver() {
        searchVM.getResponseMsg().observe(viewLifecycleOwner, Observer {
            binding.EditTextPokemonName.text = null
            when (it) {
                Constants.MSGS.SUCCESS -> {
                    Toast.makeText(context, R.string.success_search, Toast.LENGTH_SHORT).show()
                }

                Constants.MSGS.NOT_FOUND -> {
                    Toast.makeText(context, R.string.fail_seach, Toast.LENGTH_SHORT).show()

                }

                Constants.MSGS.FAIL -> {
                    Toast.makeText(context, searchVM.failDetails, Toast.LENGTH_SHORT).show()
                }
            }
        })

        searchVM.getPokemon().observe(viewLifecycleOwner, Observer {
            val intent = Intent(context, VisualizePokemonActivity::class.java)
            val pokemon = searchVM.getPokemon().value

            val bundle = bundleOf(
                "pokemon" to pokemon
            )

            intent.putExtras(bundle)
            startActivity(intent)
        })

        searchVM.logout.observe(viewLifecycleOwner, Observer {
            startActivity(Intent(context, LoginActivity::class.java))
            requireActivity().finish()
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}