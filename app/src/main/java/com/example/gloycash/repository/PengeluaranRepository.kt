package com.example.gloycash.repository

import com.example.gloycash.model.AllPengeluaranResponse
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.service_api.PengeluaranService

interface PengeluaranRepository{
    suspend fun insertPengeluaran(pengeluaran: Pengeluaran)
    suspend fun getPengeluaran(): AllPengeluaranResponse
    suspend fun getPengeluaranById(id: Int): Pengeluaran
    suspend fun updatePengeluaran(id: Int, pengeluaran: Pengeluaran)
    suspend fun deletePengeluaran(id: Int)
}
class NetworkPengeluaranRepository(
    private val pengeluaranApiService: PengeluaranService
): PengeluaranRepository {
    override suspend fun insertPengeluaran(pengeluaran: Pengeluaran) {
        pengeluaranApiService.insertPengeluaran(pengeluaran)
    }

    override suspend fun getPengeluaran(): AllPengeluaranResponse {
        return pengeluaranApiService.getPengeluaran()
    }

    override suspend fun getPengeluaranById(id: Int): Pengeluaran {
        return pengeluaranApiService.getPengeluaranById(id).data
    }

    override suspend fun updatePengeluaran(id: Int, pengeluaran: Pengeluaran) {
        pengeluaranApiService.updatePengeluaran(id, pengeluaran)
    }

    override suspend fun deletePengeluaran(id: Int) {
        pengeluaranApiService.deletePengeluaran(id)
    }
}