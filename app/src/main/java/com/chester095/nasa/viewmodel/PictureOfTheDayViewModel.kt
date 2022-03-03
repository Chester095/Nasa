package com.chester095.nasa.viewmodel

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chester095.nasa.BuildConfig
import com.chester095.nasa.repository.dto.PODServerResponseData
import com.chester095.nasa.repository.RetrofitApiImpl
import com.chester095.nasa.repository.dto.EarthEpicServerResponseData
import com.chester095.nasa.repository.dto.MarsPhotosServerResponseData
import com.chester095.nasa.repository.dto.SolarFlareResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PictureOfTheDayViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val retrofitApiImpl: RetrofitApiImpl = RetrofitApiImpl()
) : ViewModel() {

    companion object {
        private const val API_ERROR = "You need API Key"
        private const val UNKNOWN_ERROR = "Unidentified error"
    }


    fun getLiveData(): LiveData<AppState> {
        return liveDataToObserve
    }

    fun getPODFromServer(day: Int) {
        val date = getDate(day)
        liveDataToObserve.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR))
        } else {
            retrofitApiImpl.getPictureOfTheDay(apiKey, date, PODCallback)
        }
    }

    fun getMarsPicture() {
        liveDataToObserve.postValue(AppState.Loading)
        val earthDate = getDayBeforeYesterday()
        retrofitApiImpl.getMarsPictureByDate(earthDate,BuildConfig.NASA_API_KEY, marsCallback)
    }

    fun getDayBeforeYesterday(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(2)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, -2)
            s.format(cal.time)
        }
    }

    private fun getDate(day: Int): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val yesterday = LocalDateTime.now().minusDays(day.toLong())
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            yesterday.format(formatter)
        } else {
            val cal: Calendar = Calendar.getInstance()
            val s = SimpleDateFormat("yyyy-MM-dd")
            cal.add(Calendar.DAY_OF_YEAR, (-day))
            s.format(cal.time)
        }
    }

    private val PODCallback = object : Callback<PODServerResponseData>{
        override fun onResponse(
            call: Call<PODServerResponseData>,
            responseData: Response<PODServerResponseData>
        ) {
            if(responseData.isSuccessful&&responseData.body()!=null){
                liveDataToObserve.value = AppState.SuccessPOD(responseData.body()!!)
            }else{
                liveDataToObserve.value = AppState.Error(IllegalStateException("Ошибка"))
            }
        }

        override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
            liveDataToObserve.value = AppState.Error(IllegalStateException("onFailure"))
        }
    }

    val marsCallback = object : Callback<MarsPhotosServerResponseData> {

        override fun onResponse(
            call: Call<MarsPhotosServerResponseData>,
            response: Response<MarsPhotosServerResponseData>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.SuccessMars(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<MarsPhotosServerResponseData>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }

    // Earth Polychromatic Imaging Camera
    fun getEpic() {
        liveDataToObserve.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            AppState.Error(Throwable(API_ERROR))
        } else {
            retrofitApiImpl.getEPIC(apiKey, epicCallback)
        }
    }

    private val epicCallback = object : Callback<List<EarthEpicServerResponseData>> {

        override fun onResponse(
            call: Call<List<EarthEpicServerResponseData>>,
            response: Response<List<EarthEpicServerResponseData>>,
        ) {
            if (response.isSuccessful && response.body() != null) {
                liveDataToObserve.postValue(AppState.SuccessEarthEpic(response.body()!!))
            } else {
                val message = response.message()
                if (message.isNullOrEmpty()) {
                    liveDataToObserve.postValue(AppState.Error(Throwable(UNKNOWN_ERROR)))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Throwable(message)))
                }
            }
        }

        override fun onFailure(call: Call<List<EarthEpicServerResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }

    fun getSolarFlare(day: Int){
        liveDataToObserve.postValue(AppState.Loading)
        val apiKey = BuildConfig.NASA_API_KEY
        if(apiKey.isBlank()){
            //
        }else{
            retrofitApiImpl.getSolarFlare(apiKey,solarFlareCallback,getDate(day))
        }
    }

    private val solarFlareCallback  = object : Callback<List<SolarFlareResponseData>>{
        override fun onResponse(
            call: Call<List<SolarFlareResponseData>>,
            response: Response<List<SolarFlareResponseData>>
        ) {
            if(response.isSuccessful && response.body()!=null){
                liveDataToObserve.postValue(AppState.SuccessWeather(response.body()!!))
            }else{
                // TODO HW
            }
        }

        override fun onFailure(call: Call<List<SolarFlareResponseData>>, t: Throwable) {
            liveDataToObserve.postValue(AppState.Error(t))
        }
    }
}