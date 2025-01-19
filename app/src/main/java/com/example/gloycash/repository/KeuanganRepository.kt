package com.example.gloycash.repository

import com.example.gloycash.model.SaldoResponse
import com.example.gloycash.model.TotalPendapatanResponse
import com.example.gloycash.model.TotalPengeluaranResponse
import com.example.gloycash.service_api.KeuanganService

interface KeuanganRepository {
    suspend fun getSaldo(): SaldoResponse
    suspend fun getTotalPendapatan(): TotalPendapatanResponse
    suspend fun getTotalPengeluaran(): TotalPengeluaranResponse
}
class NetworkKeuanganRepository(private val keuanganApiService: KeuanganService) : KeuanganRepository {
    override suspend fun getSaldo(): SaldoResponse {
        return keuanganApiService.getSaldo()
    }

    override suspend fun getTotalPendapatan(): TotalPendapatanResponse {
        return keuanganApiService.getTotalPendapatan()
    }

    override suspend fun getTotalPengeluaran(): TotalPengeluaranResponse {
        return keuanganApiService.getTotalPengeluaran()
    }
}