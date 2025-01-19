package com.example.gloycash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pendapatan(
    @SerialName("id_pendapatan")
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
data class AllPendapatanResponse(
    val status: Boolean,
    val message: String,
    val data: List<Pendapatan>
)

@Serializable
data class PendapatanDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Pendapatan
)
