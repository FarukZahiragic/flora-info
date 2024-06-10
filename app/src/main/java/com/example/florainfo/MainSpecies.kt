package com.example.florainfo

import com.google.gson.annotations.SerializedName

data class MainSpecies(
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String?,
    @SerializedName("main_species") val mainSpecies: SerializedBiljkaData
)
