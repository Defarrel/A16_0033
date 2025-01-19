package com.example.gloycash.model

import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pengeluaran(
    @SerialName("id_pengeluaran")
    val id: Int,
    @SerialName("id_aset")
    val idAset: Int,
    @SerialName("id_kategori")
    val idKategori: Int,
    @SerialName("tanggal_transaksi")
    val tanggalTransaksi: String,
    @SerialName("total")
    val total: Float,
    @SerialName("catatan")
    val catatan: String
)

@Serializable
data class AllPengeluaranResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pengeluaran>
)

@Serializable
data class PengeluaranDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pengeluaran
)
