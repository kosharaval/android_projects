package com.example.readonlinejson.data

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.readonlinejson.WEB_SERVICE_URL
import com.example.readonlinejson.utilities.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DataRepository (app : Application)  {
    // https://developer.android.com/reference/androidx/lifecycle/MutableLiveData.html
    private val monsterData = MutableLiveData<List<Monster>>()
    private val monsterDao = MonsterDatabase.getDatabase(app)
        .monsterDao()                                           // instantiates the database

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val data = monsterDao.getAll()      // get data from SQLite
            if (data.isEmpty()) {
                callWebService(app)
            } else {
                monsterData.postValue(data)     // stores the locally retrieved data in a List
                withContext(Dispatchers.Main) {
                    Toast.makeText(app, "Using local data", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun getMonsterData() : MutableLiveData<List<Monster>> {
        return monsterData
    }

    @WorkerThread
    suspend fun callWebService(app: Application) {
        if (NetworkHelper.isNetworkConnected(app)) {
            withContext(Dispatchers.Main) {
                Toast.makeText(app, "Using remote data", Toast.LENGTH_LONG).show()
            }
            Log.i("Main DataRepository", "Calling web service")
            val retrofit = Retrofit.Builder()
                .baseUrl(WEB_SERVICE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
            val service = retrofit.create(MonsterService::class.java)
            val serviceData = service.getMonsterData().body() ?: emptyList()

            Log.i("Main DataRepository","${serviceData}")
            monsterData.postValue(serviceData)
            monsterDao.deleteAll()                      // deletes all previous data in SQLite
            monsterDao.insertMonsters(serviceData)      // insert all data retrieved from web
        }
    }
}