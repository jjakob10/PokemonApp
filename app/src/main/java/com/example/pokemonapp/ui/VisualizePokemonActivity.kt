package com.example.pokemonapp.ui

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.pokemonapp.R
import com.example.pokemonapp.databinding.ActivityVisualizePokemonBinding
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import com.example.pokemonapp.viewModel.VisualizePokemonViewModel

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




    }

    override fun onClick(view: View) {
        if (view.id == R.id.image_back) {
            finish()
        }
    }
}