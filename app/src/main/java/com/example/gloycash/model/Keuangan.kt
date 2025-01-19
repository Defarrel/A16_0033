package com.example.gloycash.model

data class SaldoResponse(
    val status: Boolean,
    val saldo: Float,
    val statusText: String
)
data class TotalPendapatanResponse(
    val status: Boolean,
    val totalPendapatan: Float
)
data class TotalPengeluaranResponse(
    val status: Boolean,
    val totalPengeluaran: Float
)