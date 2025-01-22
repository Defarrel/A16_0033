package com.example.gloycash.model

data class SaldoResponse(
    val status: Boolean,
    val saldo: Float,
    val statusText: String
)
data class TotalPengeluaranResponse(
    val status: Boolean,
    val totalPengeluaran: Float
)