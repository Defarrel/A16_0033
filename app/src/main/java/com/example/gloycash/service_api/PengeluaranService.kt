package com.example.gloycash.service_api

import com.example.gloycash.model.AllPendapatanResponse
import com.example.gloycash.model.AllPengeluaranResponse
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.model.PendapatanDetailResponse
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.model.PengeluaranDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PengeluaranService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("store")
    suspend fun insertPengeluaran(@Body pengeluaran: Pengeluaran)

    @GET("pendapatan/pengeluaran")
    suspend fun getPengeluaran(): AllPengeluaranResponse

    @GET("{id}")
    suspend fun getPengeluaranById(@Path("id") id: Int): PengeluaranDetailResponse

    @PUT("{id}")
    suspend fun updatePengeluaran(@Path("id") id: Int, @Body pengeluaran: Pengeluaran)

    @DELETE("{id}")
    suspend fun deletePengeluaran(@Path("id") id: Int): Response<Void>
}