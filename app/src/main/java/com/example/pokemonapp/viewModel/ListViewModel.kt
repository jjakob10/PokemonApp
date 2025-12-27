package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.pokemonApi.client.ClientRetrofit
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import com.example.pokemonapp.repository.pokemonApi.model.PokemonListEntity
import com.example.pokemonapp.repository.pokemonApi.service.PokemonService
import com.example.pokemonapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.net.Uri
import com.example.pokemonapp.repository.data.model.PokemonModel
import java.util.Locale.getDefault


class ListViewModel  (application: Application) : AndroidViewModel(application){
    private var pokemonList = MutableLiveData(listOf<PokemonModel>())
    val listMsg = MutableLiveData<Int>()

    var offset = 0

    val api = ClientRetrofit.createService(PokemonService::class.java)


    fun getPokemonList(): LiveData<List<PokemonModel>> {
        return pokemonList
    }

    fun getListMsg(): LiveData<Int> {
        return listMsg
    }

    fun processResponse(pokemonListResp: PokemonListEntity) {
        pokemonListResp.next

        val url = pokemonListResp.next
        val uri = Uri.parse(url)
        offset = uri.getQueryParameter("offset")!!.toInt()

        for (pokemon in pokemonListResp.results) {

            val uri = Uri.parse(pokemon.url)
            val lastSegment = uri.lastPathSegment?.toInt()

            val resp: Call<PokemonEntity> = api.getPokemonByName(lastSegment.toString())

            resp.enqueue(object : Callback<PokemonEntity>{

                override fun onResponse(
                    call: Call<PokemonEntity>,
                    response: Response<PokemonEntity>
                ) {

                    if (response.isSuccessful) {
                        val pokemon = response.body()!!

                        val pm = PokemonModel().apply {
                            name = pokemon.name.lowercase(getDefault())
                            maleImageUrl = pokemon.sprites.front_default  ?: ""
                            femaleImageUrl = pokemon.sprites.front_female ?: ""

                            // Abilities (3 fixos)
                            ability1 = pokemon.abilities.getOrNull(0)?.ability?.name ?: ""
                            ability2 = pokemon.abilities.getOrNull(1)?.ability?.name ?: ""
                            ability3 = pokemon.abilities.getOrNull(2)?.ability?.name ?: ""

                            // Stats (6 fixos)
                            hp = pokemon.stats[0].base_stat
                            attack = pokemon.stats[1].base_stat
                            defense = pokemon.stats[2].base_stat
                            specialAttack = pokemon.stats[3].base_stat
                            specialDefense = pokemon.stats[4].base_stat
                            speed = pokemon.stats[5].base_stat
                        }
                        val currentList = pokemonList.value
                        if (currentList != null) {
                            val newList = currentList.toMutableList()
                            newList.add(pm)
                            pokemonList.value = newList
                        }

                    }else{
                        listMsg.value = Constants.MSGS.FAIL
                    }


                }

                override fun onFailure(call: Call<PokemonEntity>, t: Throwable) {
                    listMsg.value = Constants.MSGS.FAIL
                }
            })


        }

    }

    fun loadMore() {
        val resp: Call<PokemonListEntity> = api.getPokemonList(limit = 18, offset= offset)

        resp.enqueue(object : Callback<PokemonListEntity>{

            override fun onResponse(
                call: Call<PokemonListEntity>,
                response: Response<PokemonListEntity>
            ) {

                if (response.isSuccessful) {
                    listMsg.value = Constants.MSGS.SUCCESS
                    val pokemonListResponse = response.body()
                    processResponse(pokemonListResponse!!)

                }else{
                    listMsg.value = Constants.MSGS.FAIL
                }


            }

            override fun onFailure(call: Call<PokemonListEntity>, t: Throwable) {
                listMsg.value = Constants.MSGS.FAIL
            }
        })



    }

}