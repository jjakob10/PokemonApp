package com.example.pokemonapp.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityMainBinding
import com.example.pokemonapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainVM = ViewModelProvider(this).get(MainViewModel::class.java)
        setObserver()

        binding.searchIcon.setOnClickListener(this)
        binding.favoriteIcon.setOnClickListener(this)
        binding.listIcon.setOnClickListener(this)

        setFragmentSearch()
    }

    private fun setObserver() {
        mainVM.getSelected().observe(this, Observer {
            var color_search: Int
            var color_favorite: Int
            var color_list: Int

            if (it == 0) {
                setFragmentSearch()
                color_search = R.color.white
                color_favorite = R.color.md_theme_inverseSurface
                color_list = R.color.md_theme_inverseSurface
            } else if (it == 1) {
                setFragmentFavorite()
                color_search = R.color.md_theme_inverseSurface
                color_favorite = R.color.white
                color_list = R.color.md_theme_inverseSurface
            } else {
                setFragmentList()
                color_search = R.color.md_theme_inverseSurface
                color_favorite = R.color.md_theme_inverseSurface
                color_list = R.color.white
            }

            binding.searchIcon.setColorFilter(ContextCompat.getColor(this, color_search))
            binding.favoriteIcon.setColorFilter(
                ContextCompat.getColor(
                    this,
                    color_favorite
                )

            )
            binding.listIcon.setColorFilter(
                ContextCompat.getColor(
                    this,
                    color_list
                )
            )

        })

    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.search_icon -> {
                mainVM.selectSearch()

            }

            R.id.favorite_icon -> {
                mainVM.selectFavorite()
            }

            R.id.list_icon -> {
                mainVM.selectList()
            }
        }
    }

    fun setFragmentSearch() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SearchFragment>(R.id.main_fragment_container_view)
        }
    }

    fun setFragmentFavorite() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FavoriteListFragment>(R.id.main_fragment_container_view)
        }
    }

    fun setFragmentList() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<ListFragment>(R.id.main_fragment_container_view)
        }
    }




}