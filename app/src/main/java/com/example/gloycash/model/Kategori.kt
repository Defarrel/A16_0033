package com.example.gloycash.model
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Kategori(
    @SerialName("id_kategori")
    val id: Int = 0,
    @SerialName("nama_kategori")
    val namaKategori: String
)

@Serializable
data class AllKategoriResponse(
    val status: Boolean,
    val message: String,
    val data: List<Kategori>
)

@Serializable
data class KategoriDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Kategori
)