package com.example.gloycash.service_api

import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.model.Aset
import com.example.gloycash.model.AsetDetailResponse
import com.example.gloycash.model.Kategori
import com.example.gloycash.model.KategoriDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface KategoriService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("kategori/store")
    suspend fun insertKategori(@Body kategori: Kategori)

    @GET("kategori")
    suspend fun getKategori(): AllKategoriResponse

    @GET("kategori/{id}")
    suspend fun getKategoriById(@Path("id") id: Int): KategoriDetailResponse

    @PUT("kategori/{id}")
    suspend fun updateKategori(@Path("id") id: Int, @Body kategori: Kategori)

    @DELETE("kategori/{id}")
    suspend fun deleteKategori(@Path("id") id: Int): Response<Void>
}