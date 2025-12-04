package com.example.pokemonapp.repository.pokemonApi.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientRetrofit {
    companion object {
        private lateinit var INSTANCE: Retrofit
        private const val BASE_URL = "https://pokeapi.co/"

        private fun getRetrofitInstance(): Retrofit {
            val http = OkHttpClient.Builder()
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(http.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return INSTANCE
        }
        fun <S> createService(className: Class<S>): S {
            return getRetrofitInstance().create(className)
        }
    }
}