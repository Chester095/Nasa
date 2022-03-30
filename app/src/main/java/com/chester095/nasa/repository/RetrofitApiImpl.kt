package com.chester095.nasa.repository

import com.chester095.nasa.repository.dto.EarthEpicServerResponseData
import com.chester095.nasa.repository.dto.MarsPhotosServerResponseData
import com.chester095.nasa.repository.dto.PODServerResponseData
import com.chester095.nasa.repository.dto.SolarFlareResponseData
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitApiImpl {
    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }

/*    fun getRetrofitImpl():RetrofitApi{
        // вынести куда-нибудь, чтобы не создавался ретрофит на любой вызов
        val podRetrofitImpl = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
        return podRetrofitImpl.create(RetrofitApi::class.java)
    }*/

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(RetrofitApi::class.java)
    }

    fun getPictureOfTheDay(apiKey: String, date: String, podCallback: Callback<PODServerResponseData>) {
        api.getPictureOfTheDay(apiKey, date).enqueue(podCallback)
    }

    // Earth Polychromatic Imaging Camera
    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        api.getEPIC(apiKey).enqueue(epicCallback)
    }

    fun getMarsPictureByDate(earth_date: String, apiKey: String, marsCallbackByDate: Callback<MarsPhotosServerResponseData>) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }

    fun getSolarFlare(apiKey: String, podCallback: Callback<List<SolarFlareResponseData>>, startDate:String="2021-09-07") {
        api.getSolarFlare(apiKey,startDate).enqueue(podCallback)
    }
}