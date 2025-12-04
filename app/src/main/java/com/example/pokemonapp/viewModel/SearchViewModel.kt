package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.pokemonApi.client.ClientRetrofit
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import com.example.pokemonapp.repository.pokemonApi.service.PokemonService
import com.example.pokemonapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel (application: Application) : AndroidViewModel(application){

    val responseMsg = MutableLiveData<Int>()

    var failDetails = ""

    val pokemon = MutableLiveData<PokemonEntity>()

    fun getResponseMsg(): LiveData<Int> {
        return responseMsg
    }

    fun getPokemon(): LiveData<PokemonEntity> {
        return pokemon
    }


    val api = ClientRetrofit.createService(PokemonService::class.java)

    fun search(pokemonName: String) {
        val resp: Call<PokemonEntity> = api.getPokemonByName(pokemonName)

        resp.enqueue(object : Callback<PokemonEntity>{

            override fun onResponse(
                call: Call<PokemonEntity>,
                response: Response<PokemonEntity>
            ) {

                if (response.isSuccessful) {
                    responseMsg.value = Constants.MSGS.SUCCESS
                    pokemon.value = response.body()
                }else{
                    responseMsg.value = Constants.MSGS.NOT_FOUND
                }


            }

            override fun onFailure(call: Call<PokemonEntity>, t: Throwable) {
                failDetails = "${t.localizedMessage}"
                responseMsg.value = Constants.MSGS.FAIL
            }
        })



    }


}