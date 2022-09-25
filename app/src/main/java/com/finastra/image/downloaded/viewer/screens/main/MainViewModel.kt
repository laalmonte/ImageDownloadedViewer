package com.finastra.image.downloaded.viewer.screens.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finastra.image.downloaded.viewer.api.ApiService
import com.finastra.image.downloaded.viewer.data.Track
import com.finastra.image.downloaded.viewer.data.Tracks
import com.finastra.image.downloaded.viewer.api.RetroInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    var trackLiveData: MutableLiveData<List<Track>> = MutableLiveData()

    init {

    }

    fun getLiveDataObserver(): MutableLiveData<List<Track>>{
        return trackLiveData
    }

    fun getTracks(){
        val retroInstance = RetroInstance.getRetroInstance()
        val apiService    = retroInstance.create(ApiService::class.java)
        val call          = apiService.getData()
        call.enqueue(object : Callback<Tracks> {
            override fun onResponse(call: Call<Tracks>, response: Response<Tracks>) {
                val trackList: List<Track> = response.body()!!.results
                trackLiveData.postValue(trackList)
            }

            override fun onFailure(call: Call<Tracks>, t: Throwable) {
                trackLiveData.postValue(null)
            }
        })
    }
}