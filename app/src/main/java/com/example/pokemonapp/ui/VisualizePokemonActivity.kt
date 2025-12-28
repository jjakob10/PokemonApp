package com.example.pokemonapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityVisualizePokemonBinding
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import com.example.pokemonapp.viewModel.VisualizePokemonViewModel
import com.google.android.material.snackbar.Snackbar

class VisualizePokemonActivity : AppCompatActivity()  , View.OnClickListener {

        private lateinit var binding: ActivityVisualizePokemonBinding;
        private lateinit var visualizeVM: VisualizePokemonViewModel

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityVisualizePokemonBinding.inflate(layoutInflater)
            setContentView(binding.root)

            visualizeVM = ViewModelProvider(this).get(VisualizePokemonViewModel::class.java)

            setObserver()
            binding.imageBack.setOnClickListener(this)
            binding.favoriteIcon.setOnClickListener(this)

            val pokemon = intent.getSerializableExtra("pokemon") as PokemonEntity
            visualizeVM.fillContent(pokemon)
        }

    private fun setObserver() {
        visualizeVM.getPokemonImageUrl().observe(this, Observer {

            val imageView = findViewById<ImageView>(R.id.pokemon_image)

            imageView.load(it) {
                crossfade(true) // Optional: add a crossfade animation
                placeholder(R.drawable.ic_pokemon_placeholder) // Optional: show a placeholder
                error(R.drawable.ic_no_image) // Optional: show an error image
            }
        })

        visualizeVM.getPokemonMale().observe(this, Observer {
            if(it){
                binding.maleIcon.setBackgroundColor(ContextCompat.getColor(this, R.color.male_blue))
                binding.maleIcon.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_background))
            }

        })

        visualizeVM.getPokemonFemale().observe(this, Observer {
            if(it){
                binding.femaleIcon.setBackgroundColor(ContextCompat.getColor(this, R.color.female_pink))
                binding.femaleIcon.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_background))
            }

        })

        visualizeVM.getPokemonAbilities().observe(this, Observer {
            binding.visualizeAbilities.text =  "$it"
        })
        visualizeVM.getPokemonName().observe(this, Observer {
            binding.vizualizePokemonName.text =  "$it"
        })

        visualizeVM.getPokemonHp().observe(this, Observer {
            binding.textPokemonHpAnswer.text =  "$it"
            binding.hpProgress.progress = it.toInt() * 100 /255
        })
        visualizeVM.getPokemonAttack().observe(this, Observer {
            binding.textPokemonAttackAnswer.text = "$it"
            binding.attackProgress.progress = it.toInt() * 100 / 255
        })

        visualizeVM.getPokemonDefense().observe(this, Observer {
            binding.textPokemonDefenseAnswer.text = "$it"
            binding.defenseProgress.progress = it.toInt() * 100 / 255
        })

        visualizeVM.getPokemonSpecialAttack().observe(this, Observer {
            binding.textPokemonSpecialAttackAnswer.text = "$it"
            binding.specialAttackProgress.progress = it.toInt() * 100 / 255
        })

        visualizeVM.getPokemonSpecialDefense().observe(this, Observer {
            binding.textPokemonSpecialDefenseAnswer.text = "$it"
            binding.specialDefenseProgress.progress = it.toInt() * 100 / 255
        })

        visualizeVM.getPokemonSpeed().observe(this, Observer {
            binding.textPokemonSpeedAnswer.text = "$it"
            binding.speedProgress.progress = it.toInt() * 100 / 255
        })

        visualizeVM.getPokemonFavorite().observe(this, Observer {
          if(it){
              binding.favoriteIcon.setColorFilter(ContextCompat.getColor(this, R.color.hp_red))
          }
          else{
              binding.favoriteIcon.setColorFilter(ContextCompat.getColor(this, R.color.md_theme_onSecondaryContainer))
          }
        })



    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_back) {
            finish()
        }
        else if(view.id == R.id.favorite_icon){
            if(!visualizeVM.pokemonFavorite.value!!){
                val snack = Snackbar.make(binding.visualizeView, R.string.add_favorite, Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(Color.DKGRAY)
                snack.setTextColor(Color.WHITE)
                snack.setTextMaxLines(1)

                snack.setAction(R.string.undo, View.OnClickListener {
                    visualizeVM.toggleFavorite()
                    makeSnack(R.string.delete_sucess)
                })

                snack.show()

            }else{
                val snack = Snackbar.make(binding.visualizeView, R.string.delete_sucess, Snackbar.LENGTH_SHORT)
                snack.setBackgroundTint(Color.DKGRAY)
                snack.setTextColor(Color.WHITE)
                snack.setTextMaxLines(1)

                snack.setAction(R.string.undo, View.OnClickListener {
                    visualizeVM.toggleFavorite()
                    makeSnack(R.string.delete_cancel)
                })

                snack.show()
            }
            visualizeVM.toggleFavorite()

        }

    }

    private fun makeSnack(msg: Int){
        val snack = Snackbar.make(binding.visualizeView, msg, Snackbar.LENGTH_SHORT)
        snack.setBackgroundTint(Color.DKGRAY)
        snack.setTextColor(Color.WHITE)
        snack.setTextMaxLines(1)
        snack.show()
    }
}