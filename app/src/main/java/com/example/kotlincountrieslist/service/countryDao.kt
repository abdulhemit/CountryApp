package com.example.kotlincountrieslist.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountrieslist.model.CountryModel


@Dao
interface countryDao {


    @Insert
    suspend fun insertAllCountries(vararg countries: CountryModel):List<Long>

    @Query("SELECT * FROM COUNTRYMODEL")
    suspend fun getAllCountries():List<CountryModel>

    @Query("SELECT * FROM COUNTRYMODEL WHERE uuid = :countryId ")
    suspend fun getCountry(countryId: Int):CountryModel

    @Query("DELETE FROM COUNTRYMODEL")
    suspend fun deleteAllCountries()

}