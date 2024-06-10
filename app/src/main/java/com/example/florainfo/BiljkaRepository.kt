package com.example.florainfo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

object BiljkaRepository {
    suspend fun getBiljkeImage(scientificName: String) : GetImageResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getBiljkaImage(scientificName)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun getBiljkeData(scientificName: String) : GetBiljkeDataResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.getBiljkaData(scientificName)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

    suspend fun searchPlant(substring: String, flowerColor: String) : GetBiljkeResponse?{
        return withContext(Dispatchers.IO) {
            var response = ApiAdapter.retrofit.searchPlants(substring, flowerColor)
            val responseBody = response.body()
            return@withContext responseBody
        }
    }

}