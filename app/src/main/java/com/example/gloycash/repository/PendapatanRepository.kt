package com.example.gloycash.repository

import com.example.gloycash.model.AllPendapatanResponse
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.model.TotalPendapatanResponse
import com.example.gloycash.service_api.PendapatanService

interface PendapatanRepository {
    suspend fun insertPendapatan(pendapatan: Pendapatan)
    suspend fun getPendapatan(): AllPendapatanResponse
    suspend fun getPendapatanById(id: Int): Pendapatan
    suspend fun updatePendapatan(id: Int, pendapatan: Pendapatan)
    suspend fun deletePendapatan(id: Int)
    suspend fun getTotalPendapatan(): TotalPendapatanResponse

}

class NetworkPendapatanRepository(
    private val pendapatanApiService: PendapatanService
) : PendapatanRepository {
    override suspend fun insertPendapatan(pendapatan: Pendapatan) {
        pendapatanApiService.insertPendapatan(pendapatan)
    }

    override suspend fun getPendapatan(): AllPendapatanResponse {
        return pendapatanApiService.getPendapatan()
    }

    override suspend fun getPendapatanById(id: Int): Pendapatan {
        return pendapatanApiService.getPendapatanById(id).data
    }

    override suspend fun updatePendapatan(id: Int, pendapatan: Pendapatan) {
        pendapatanApiService.updatePendapatan(id, pendapatan)
    }

    override suspend fun deletePendapatan(id: Int) {
        pendapatanApiService.deletePendapatan(id)
    }
    override suspend fun getTotalPendapatan(): TotalPendapatanResponse {
        return pendapatanApiService.getTotalPendapatan()
    }
}