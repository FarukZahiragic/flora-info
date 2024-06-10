package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class GetBiljkeResponse(
    @SerializedName("data") val data: List<Slug>
)
