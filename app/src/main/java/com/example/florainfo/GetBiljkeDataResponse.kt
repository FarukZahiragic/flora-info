package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class GetBiljkeDataResponse(
    @SerializedName("data") val data: MainSpecies
)
