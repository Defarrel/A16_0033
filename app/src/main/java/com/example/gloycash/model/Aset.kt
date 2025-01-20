package com.example.gloycash.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Aset(
    @SerialName("id_aset")
    val id: Int = 0,
    @SerialName("nama_aset")
    val namaAset: String
)

@Serializable
data class AllAsetResponse(
    val status: Boolean,
    val message: String,
    val data: List<Aset>
)

@Serializable
data class AsetDetailResponse(
    val status: Boolean,
    val message: String,
    val data: Aset
)