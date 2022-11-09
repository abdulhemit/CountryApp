package com.example.kotlincountrieslist.service

import com.example.kotlincountrieslist.model.CountryModel
import io.reactivex.Single
import retrofit2.http.GET

interface countryAPI {
    
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<CountryModel>>
}