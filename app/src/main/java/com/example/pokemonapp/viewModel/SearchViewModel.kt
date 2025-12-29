package com.example.pokemonapp.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokemonapp.repository.data.model.PokemonModel
import com.example.pokemonapp.repository.mapper.toPokemonModel
import com.example.pokemonapp.repository.pokemonApi.client.ClientRetrofit
import com.example.pokemonapp.repository.pokemonApi.model.PokemonEntity
import com.example.pokemonapp.repository.pokemonApi.service.PokemonService
import com.example.pokemonapp.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel (application: Application) : AndroidViewModel(application){

    val responseMsg = MutableLiveData<Int>()

    var failDetails = ""

    val pokemon = MutableLiveData<PokemonModel>()

    private val _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    fun getResponseMsg(): LiveData<Int> {
        return responseMsg
    }

    fun getPokemon(): LiveData<PokemonModel> {
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
                    response.body()?.let {
                        pokemon.value = toPokemonModel(it)
                    }
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

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _logout.value = true
    }
}