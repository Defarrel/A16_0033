package com.example.gloycash.service_api

import com.example.gloycash.model.AllPendapatanResponse
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.model.PendapatanDetailResponse
import com.example.gloycash.model.TotalPendapatanResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PendapatanService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("pendapatan/store")
    suspend fun insertPendapatan(@Body pendapatan: Pendapatan)

    @GET("pendapatan")
    suspend fun getPendapatan(): AllPendapatanResponse

    @GET("pendapatan/{id}")
    suspend fun getPendapatanById(@Path("id") id: Int): PendapatanDetailResponse

    @PUT("pendapatan/{id}")
    suspend fun updatePendapatan(@Path("id") id: Int, @Body pendapatan: Pendapatan)

    @DELETE("pendapatan/{id}")
    suspend fun deletePendapatan(@Path("id") id: Int): Response<Void>

    @GET("pendapatan/total")
    suspend fun getTotalPendapatan(): TotalPendapatanResponse
}