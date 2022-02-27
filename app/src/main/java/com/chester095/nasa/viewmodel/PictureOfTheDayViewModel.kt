package com.chester095.nasa.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chester095.nasa.BuildConfig
import com.chester095.nasa.repository.PDOServerResponse
import com.chester095.nasa.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) : ViewModel() {
    fun getData(): LiveData<PictureOfTheDayData> {
        return liveDataForViewToObserve
    }

    fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(callback)
        }
    }

    fun sendServerRequest(date:String) {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(0)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(Throwable("wrong key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey,date).enqueue(callback)
        }
    }

    private val callback = object : Callback<PDOServerResponse>{
        override fun onResponse(
            call: Call<PDOServerResponse>,
            response: Response<PDOServerResponse>
        ) {
            if(response.isSuccessful&&response.body()!=null){
                liveDataForViewToObserve.value = PictureOfTheDayData.Success(response.body()!!)
            }else{
                liveDataForViewToObserve.value = PictureOfTheDayData.Error(IllegalStateException("Ошибка"))
            }
        }

        override fun onFailure(call: Call<PDOServerResponse>, t: Throwable) {
            liveDataForViewToObserve.value = PictureOfTheDayData.Error(IllegalStateException("onFailure"))
        }

    }
}