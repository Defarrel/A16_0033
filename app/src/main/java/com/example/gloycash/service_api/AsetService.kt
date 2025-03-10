package com.example.gloycash.service_api

import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.Aset
import com.example.gloycash.model.AsetDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AsetService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("aset/store")
    suspend fun insertAset(@Body aset: Aset)

    @GET("aset")
    suspend fun getAset(): AllAsetResponse

    @GET("aset/{id}")
    suspend fun getAsetById(@Path("id") id: Int): AsetDetailResponse

    @PUT("aset/{id}")
    suspend fun updateAset(@Path("id") id: Int, @Body aset: Aset)

    @DELETE("aset/{id}")
    suspend fun deleteAset(@Path("id") id: Int): Response<Void>
}