package com.example.gloycash.repository

import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.Aset
import com.example.gloycash.service_api.AsetService

interface AsetRepository {
    suspend fun insertAset(aset: Aset)
    suspend fun getAset(): AllAsetResponse
    suspend fun getAsetById(id: Int): Aset
    suspend fun updateAset(id: Int, aset: Aset)
    suspend fun deleteAset(id: Int)
}

class NetworkAsetRepository(
    private val asetApiService: AsetService
) : AsetRepository {
    override suspend fun insertAset(aset: Aset) {
        asetApiService.insertAset(aset)
    }

    override suspend fun getAset(): AllAsetResponse {
        return asetApiService.getAset()
    }

    override suspend fun getAsetById(id: Int): Aset {
        return asetApiService.getAsetById(id).data
    }

    override suspend fun updateAset(id: Int, aset: Aset) {
        asetApiService.updateAset(id, aset)
    }

    override suspend fun deleteAset(id: Int) {
        asetApiService.deleteAset(id)
    }
}