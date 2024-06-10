package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class SerializedBiljkaData(
    @SerializedName("family") val family: String?,
    @SerializedName("edible") val edible: Boolean?,
    @SerializedName("specifications") val specifications: Specifications,
    @SerializedName("growth") val growth: Growth?
)
