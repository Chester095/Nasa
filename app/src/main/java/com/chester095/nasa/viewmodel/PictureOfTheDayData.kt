package com.chester095.nasa.viewmodel

import com.chester095.nasa.repository.PDOServerResponse

sealed class PictureOfTheDayData {
    data class Success(val serverResponse: PDOServerResponse):PictureOfTheDayData()
    data class Error(val error:Throwable):PictureOfTheDayData()
    data class Loading(val process: Int?):PictureOfTheDayData()
}