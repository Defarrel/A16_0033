package com.example.gloycash.service_api

import com.example.gloycash.model.SaldoResponse
import com.example.gloycash.model.TotalPendapatanResponse
import com.example.gloycash.model.TotalPengeluaranResponse
import retrofit2.http.GET

interface KeuanganService {
    @GET("keuangan/saldo")
    suspend fun getSaldo(): SaldoResponse

    @GET("keuangan/total-pendapatan")
    suspend fun getTotalPendapatan(): TotalPendapatanResponse

    @GET("keuangan/total-pengeluaran")
    suspend fun getTotalPengeluaran(): TotalPengeluaranResponse
}