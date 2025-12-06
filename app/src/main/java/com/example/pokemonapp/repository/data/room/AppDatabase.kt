package com.example.pokemonapp.repository.data.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokemonapp.repository.data.dao.PokemonDAO
import com.example.pokemonapp.repository.data.model.PokemonModel

@Database(entities = [PokemonModel::class], version = 1)
abstract class AppDatabase(): RoomDatabase() {

    abstract fun PokemonDAO(): PokemonDAO
    companion object {
        private lateinit var INSTANCE: AppDatabase
        fun getDatabase(context: Context): AppDatabase {

            if(!::INSTANCE.isInitialized) {

                synchronized(AppDatabase::class) {

                    INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "pokemon.db")
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}