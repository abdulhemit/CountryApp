package com.example.kotlincountrieslist.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import androidx.core.content.edit

class CustomSharedPrefences {


    companion object {


        private val PREFERENCE_TIME = "preference_time"
        private var sharedPrefences : SharedPreferences?= null

        @Volatile private var instence:CustomSharedPrefences? = null

        private val lock = Any()
        operator fun invoke(context: Context):CustomSharedPrefences = instence?: synchronized(lock){
            instence?: makeCustomSharedPrefences(context).also {
                instence = it
            }
        }
        private fun makeCustomSharedPrefences(context: Context):CustomSharedPrefences{
            sharedPrefences= PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPrefences()
        }

    }
    fun saveTime(time:Long){
        sharedPrefences?.edit(commit = true){
            putLong(PREFERENCE_TIME,time)
        }
    }
    fun getTime()= sharedPrefences?.getLong(PREFERENCE_TIME,0)
}