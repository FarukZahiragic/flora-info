package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class Specifications(
    @SerializedName("toxicity") val toxicity: String?
)
