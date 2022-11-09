package com.example.kotlincountrieslist.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountrieslist.model.CountryModel


@Database(entities = arrayOf(CountryModel::class), version = 1)
abstract class CountryDatabase(): RoomDatabase() {

    abstract fun countryDao(): countryDao

    companion object{
        @Volatile private var instance : CountryDatabase? = null

        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                it.let {
                    instance = it
                }
            }
        }
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            CountryDatabase::class.java,"countryDatabase"
        ).build()

    }

}