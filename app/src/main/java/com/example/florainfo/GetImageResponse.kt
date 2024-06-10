package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class GetImageResponse(
    @SerializedName("data") val biljke: List<SerializedImage>
)
