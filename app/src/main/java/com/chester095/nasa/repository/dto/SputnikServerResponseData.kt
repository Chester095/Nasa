package com.chester095.nasa.repository.dto

import com.google.gson.annotations.SerializedName

data class SputnikServerResponseData(
        @field:SerializedName("url") val url: String?,
        @field:SerializedName("date") val date: String?,
)
