package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class MainSpeciesSearch(
    @SerializedName("family") val family: String?,
    //@SerializedName("toxicity") val toxicity: String?,
    //@SerializedName("edible") val toxicity: String?,
    @SerializedName("specifications") val specifications: Specifications,
    @SerializedName("growth") val growth: Growth
)
