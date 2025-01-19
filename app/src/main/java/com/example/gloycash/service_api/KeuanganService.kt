package com.example.gloycash.service_api

import com.example.gloycash.model.SaldoResponse
import com.example.gloycash.model.TotalPendapatanResponse
import com.example.gloycash.model.TotalPengeluaranResponse
import retrofit2.http.GET

interface KeuanganService {
    @GET("/saldo")
    suspend fun getSaldo(): SaldoResponse

    @GET("/total-pendapatan")
    suspend fun getTotalPendapatan(): TotalPendapatanResponse

    @GET("/total-pengeluaran")
    suspend fun getTotalPengeluaran(): TotalPengeluaranResponse
}