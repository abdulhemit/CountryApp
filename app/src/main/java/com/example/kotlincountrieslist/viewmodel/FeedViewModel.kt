package com.example.kotlincountrieslist.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlincountrieslist.model.CountryModel
import com.example.kotlincountrieslist.service.CountryDatabase
import com.example.kotlincountrieslist.service.countryAPIservice
import com.example.kotlincountrieslist.util.CustomSharedPrefences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.Objects

class FeedViewModel(application: Application): BaseViewModel(application) {
    private val countryAPIservice = countryAPIservice()
    private val disposable = CompositeDisposable()
    private val customPreference = CustomSharedPrefences(getApplication())
    private var resreshTime = 10 * 60 * 1000 * 1000 * 1000L

    val countries = MutableLiveData<List<CountryModel>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreference.getTime()
        if (updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < resreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }

    }
    fun refreshFromAPI(){
        getDataFromAPI()
    }
    private fun getDataFromSQLite(){
        countryLoading.value = true
        launch {
            val countries = CountryDatabase.invoke(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"Countries From SQLite",Toast.LENGTH_LONG).show()
        }
    }

    fun getDataFromAPI(){
        countryLoading.value = true
        disposable.addAll(
            countryAPIservice
                .getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<CountryModel>>(){
                    override fun onSuccess(t: List<CountryModel>) {
                        storeInSQLite(t)
                        Toast.makeText(getApplication(),"Countries From API",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        countryError.value = true
                        countryLoading.value = false
                        e.printStackTrace()
                    }

                })
        )


    }
    private fun showCountries(list: List<CountryModel>){
        countries.value = list
        countryLoading.value = false
        countryError.value = false
    }
    private fun storeInSQLite(list: List<CountryModel>){

        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            dao.getAllCountries()
            val listLong = dao.insertAllCountries(*list.toTypedArray())
            var i = 0
            while (i <list.size){
                list[i].uuid = listLong[i].toInt()
                i = i + 1
            }
            showCountries(list)

        }
        customPreference.saveTime(System.nanoTime())

    }
}