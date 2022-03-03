package com.chester095.nasa.viewmodel

import com.chester095.nasa.repository.dto.EarthEpicServerResponseData
import com.chester095.nasa.repository.dto.MarsPhotosServerResponseData
import com.chester095.nasa.repository.dto.PODServerResponseData
import com.chester095.nasa.repository.dto.SolarFlareResponseData

sealed class AppState {
    data class SuccessPOD(val serverResponseData: PODServerResponseData):AppState()
    data class SuccessEarthEpic (val serverResponseData: List<EarthEpicServerResponseData>) : AppState()
    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class SuccessWeather(val solarFlareResponseData:List<SolarFlareResponseData>) : AppState()
    data class Error(val error:Throwable):AppState()
    object Loading : AppState()
}