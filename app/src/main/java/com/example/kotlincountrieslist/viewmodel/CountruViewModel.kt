package com.example.kotlincountrieslist.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountrieslist.model.CountryModel
import com.example.kotlincountrieslist.service.CountryDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class CountryViewModel(application: Application):BaseViewModel(application) {

    val countryLiveData = MutableLiveData<CountryModel>()

    fun getDataFromRoom(uuid: Int){

        launch {
            val dao = CountryDatabase.invoke(getApplication()).countryDao()
            val country = dao .getCountry(uuid)
            countryLiveData.value = country
        }


     }
}