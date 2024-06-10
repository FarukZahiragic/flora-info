package com.example.florainfo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("plants")
    suspend fun getBiljkaImage(
        @Query("filter[scientific_name]") scientificName: String,
        @Query("token") apiKey: String = "xnH7n_lI6DbT-0tnRJh_lcZgyXtvuYCrkuHfg1v9h48"
    ): Response<GetImageResponse>

    @GET("plants/{id}")
    suspend fun getBiljkaData(
        @Path("id") scientificName: String,
        @Query("token") apiKey: String = "xnH7n_lI6DbT-0tnRJh_lcZgyXtvuYCrkuHfg1v9h48"
    ): Response<GetBiljkeDataResponse>

    @GET("plants/search")
    suspend fun searchPlants(
        @Query("q") query: String,
        @Query("filter[flower_color]") flowerColor: String,
        @Query("token") apiKey: String = "xnH7n_lI6DbT-0tnRJh_lcZgyXtvuYCrkuHfg1v9h48"
    ): Response<GetBiljkeResponse>


}