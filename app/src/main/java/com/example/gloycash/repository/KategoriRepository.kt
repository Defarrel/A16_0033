package com.example.gloycash.repository

import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.model.Kategori
import com.example.gloycash.service_api.KategoriService

interface KategoriRepository{
    suspend fun insertKategori(kategori: Kategori)
    suspend fun getKategori(): AllKategoriResponse
    suspend fun getKategoriById(id: Int): Kategori
    suspend fun updateKategori(id: Int, kategori: Kategori)
    suspend fun deleteKategori(id: Int)
}

class NetworkKategoriRepository(
    private val kategoriApiService: KategoriService
): KategoriRepository{
    override suspend fun insertKategori(kategori: Kategori) {
        kategoriApiService.insertKategori(kategori)
    }

    override suspend fun getKategori(): AllKategoriResponse {
        return kategoriApiService.getKategori()
    }

    override suspend fun getKategoriById(id: Int): Kategori {
        return kategoriApiService.getKategoriById(id).data
    }

    override suspend fun updateKategori(id: Int, kategori: Kategori) {
        kategoriApiService.updateKategori(id, kategori)
    }

    override suspend fun deleteKategori(id: Int) {
        kategoriApiService.deleteKategori(id)
    }
}