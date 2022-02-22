package com.chester095.nasa.viewmodel

import android.os.Build
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chester095.nasa.BuildConfig
import com.chester095.nasa.repository.PDOServerResponse
import com.chester095.nasa.repository.PictureOfTheDayRetrofitImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl()
) :ViewModel() {
    fun getData():LiveData<PictureOfTheDayData>{
        return liveData
    }
    fun sendRequest(){
        liveData.postValue(PictureOfTheDayData.Loading(null))
        pictureOfTheDayRetrofitImpl.getRetrofitImpl().getPictureOfTheDay(BuildConfig.NASA_API_KEY).enqueue(
            object : Callback<PDOServerResponse>{
                override fun onResponse(
                    call: Call<PDOServerResponse>,
                    response: Response<PDOServerResponse>
                ) {
                    if(response.isSuccessful&&response.body()!=null){
                        response.body()?.let {
                            liveData.postValue(PictureOfTheDayData.Success(it))
                        }
                    } else {
                        if (response.isSuccessful == null) {
                            Log.d("!!! PictureOfTheDay ", " response.isSuccessful ${response.isSuccessful}")
                        }
                    }
                }

                override fun onFailure(call: Call<PDOServerResponse>, t: Throwable) {
                    Log.d("!!! PictureOfTheDay ", " Ошибка $t")
                }
            }
        )
    }
}