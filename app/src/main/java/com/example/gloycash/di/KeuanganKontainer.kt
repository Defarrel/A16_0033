package com.example.gloycash.di

import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.repository.KeuanganRepository
import com.example.gloycash.repository.NetworkAsetRepository
import com.example.gloycash.repository.NetworkKategoriRepository
import com.example.gloycash.repository.NetworkKeuanganRepository
import com.example.gloycash.repository.NetworkPendapatanRepository
import com.example.gloycash.repository.NetworkPengeluaranRepository
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.repository.PengeluaranRepository
import com.example.gloycash.service_api.AsetService
import com.example.gloycash.service_api.KategoriService
import com.example.gloycash.service_api.KeuanganService
import com.example.gloycash.service_api.PendapatanService
import com.example.gloycash.service_api.PengeluaranService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.create

interface AppContainer {
    val keuanganRepository: KeuanganRepository
    val asetRepository: AsetRepository
    val kategoriRepository: KategoriRepository
    val pendapatanRepository: PendapatanRepository
    val pengeluaranRepository: PengeluaranRepository
}

class KeuanganKontainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:3000/keuangan"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    // Service instances
    private val keuanganService: KeuanganService by lazy {
        retrofit.create(KeuanganService::class.java)
    }
    private val asetService: AsetService by lazy {
        retrofit.create(AsetService::class.java)
    }
    private val kategoriService: KategoriService by lazy {
        retrofit.create(KategoriService::class.java)
    }
    private val pendapatanService: PendapatanService by lazy {
        retrofit.create(PendapatanService::class.java)
    }
    private val pengeluaranService: PengeluaranService by lazy {
        retrofit.create(PengeluaranService::class.java)
    }

    // Repository instances
    override val keuanganRepository: KeuanganRepository by lazy {
        NetworkKeuanganRepository(keuanganService)
    }
    override val asetRepository: AsetRepository by lazy {
        NetworkAsetRepository(asetService)
    }
    override val kategoriRepository: KategoriRepository by lazy {
        NetworkKategoriRepository(kategoriService)
    }
    override val pendapatanRepository: PendapatanRepository by lazy {
        NetworkPendapatanRepository(pendapatanService)
    }
    override val pengeluaranRepository: PengeluaranRepository by lazy {
        NetworkPengeluaranRepository(pengeluaranService)
    }
}