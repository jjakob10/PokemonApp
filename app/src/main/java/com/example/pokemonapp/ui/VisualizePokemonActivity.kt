package com.example.pokemonapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityVisualizePokemonBinding
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.viewModel.VisualizePokemonViewModel
import com.google.android.material.snackbar.Snackbar

class VisualizePokemonActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityVisualizePokemonBinding
    private lateinit var visualizeVM: VisualizePokemonViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizePokemonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        visualizeVM = ViewModelProvider(this).get(VisualizePokemonViewModel::class.java)

        setObserver()
        binding.imageBack.setOnClickListener(this)
        binding.favoriteIcon.setOnClickListener(this)

        val pokemon = intent.getSerializableExtra("pokemon") as PokemonModel
        visualizeVM.fillContent(pokemon)
    }

    private fun setObserver() {
        visualizeVM.getPokemonImageUrl().observe(this) {
            val imageView = findViewById<ImageView>(R.id.pokemon_image)
            imageView.load(it) {
                crossfade(true)
                placeholder(R.drawable.ic_pokemon_placeholder)
                error(R.drawable.ic_no_image)
            }
        }

        visualizeVM.getPokemonMale().observe(this) {
            if (it) {
                binding.maleIcon.setBackgroundColor(ContextCompat.getColor(this, R.color.male_blue))
                binding.maleIcon.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_background))
            }
        }

        visualizeVM.getPokemonFemale().observe(this) {
            if (it) {
                binding.femaleIcon.setBackgroundColor(ContextCompat.getColor(this, R.color.female_pink))
                binding.femaleIcon.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_background))
            }
        }

        visualizeVM.getPokemonAbilities().observe(this) {
            binding.visualizeAbilities.text = it
        }

        visualizeVM.getPokemonName().observe(this) {
            binding.vizualizePokemonName.text = it
        }

        visualizeVM.getPokemonHp().observe(this) {
            binding.textPokemonHpAnswer.text = "$it"
            binding.hpProgress.progress = it * 100 / 255
        }

        visualizeVM.getPokemonAttack().observe(this) {
            binding.textPokemonAttackAnswer.text = "$it"
            binding.attackProgress.progress = it * 100 / 255
        }

        visualizeVM.getPokemonDefense().observe(this) {
            binding.textPokemonDefenseAnswer.text = "$it"
            binding.defenseProgress.progress = it * 100 / 255
        }

        visualizeVM.getPokemonSpecialAttack().observe(this) {
            binding.textPokemonSpecialAttackAnswer.text = "$it"
            binding.specialAttackProgress.progress = it * 100 / 255
        }

        visualizeVM.getPokemonSpecialDefense().observe(this) {
            binding.textPokemonSpecialDefenseAnswer.text = "$it"
            binding.specialDefenseProgress.progress = it * 100 / 255
        }

        visualizeVM.getPokemonSpeed().observe(this) {
            binding.textPokemonSpeedAnswer.text = "$it"
            binding.speedProgress.progress = it * 100 / 255
        }

        visualizeVM.getPokemonFavorite().observe(this) {
            if (it) {
                binding.favoriteIcon.setColorFilter(ContextCompat.getColor(this, R.color.hp_red))
            } else {
                binding.favoriteIcon.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_onSecondaryContainer))
            }
        }

        visualizeVM.favoriteOpStatus.observe(this) { status ->
            when (status) {
                is VisualizePokemonViewModel.FavoriteOpStatus.ADD_SUCCESS -> {
                    showSnackbarWithUndo(R.string.pokemon_added_to_favorites) {
                        visualizeVM.removeFavorite()
                    }
                    visualizeVM.onSnackbarShown()
                }
                is VisualizePokemonViewModel.FavoriteOpStatus.DELETE_SUCCESS -> {
                    showSnackbarWithUndo(R.string.pokemon_removed_from_favorites) {
                        visualizeVM.addFavorite()
                    }
                    visualizeVM.onSnackbarShown()
                }
                is VisualizePokemonViewModel.FavoriteOpStatus.OP_ERROR -> {
                    showSnackbar(getString(R.string.error_updating_favorites))
                    visualizeVM.onSnackbarShown()
                }
                else -> { /* DO NOTHING */ }
            }
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_back -> finish()
            R.id.favorite_icon -> visualizeVM.toggleFavorite()
        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showSnackbarWithUndo(messageResId: Int, onUndo: () -> Unit) {
        Snackbar.make(binding.root, messageResId, Snackbar.LENGTH_LONG)
            .setAction(R.string.undo) { onUndo() }
            .show()
    }
}
