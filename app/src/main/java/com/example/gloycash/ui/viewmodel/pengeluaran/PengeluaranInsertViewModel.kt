package com.example.gloycash.ui.viewmodel.pengeluaran

import InsertUiState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.repository.PengeluaranRepository

import kotlinx.coroutines.launch

class PengeluaranInsertViewModel(
    private val pengeluaranRepository: PengeluaranRepository,
    private val kategoriRepository: KategoriRepository,
    private val asetRepository: AsetRepository
) : ViewModel() {

    var uiState by mutableStateOf(PengeluaranInsertUiState())
        private set
    var snackBarHostState = SnackbarHostState()
        private set

    init {
        loadKategoriDanAset()
    }

    fun loadKategoriDanAset() {
        viewModelScope.launch {
            val kategori = kategoriRepository.getKategori()
            val aset = asetRepository.getAset()
            uiState = uiState.copy(
                kategoriList = kategori,
                asetList = aset
            )
        }
    }

    fun updateInsertPengeluaranState(pengeluaranInsertEvent: PengeluaranInsertEvent) {
        uiState = uiState.copy(
            insertUiEvent = pengeluaranInsertEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertUiEvent
        val errorState = FormErrorStatePengeluaran(
            idKategori = if (event.idKategori.toIntOrNull() != null) null else "Nama Kategori tidak valid",
            idAset = if (event.idAset.toIntOrNull() != null) null else "Nama Aset tidak valid",
            total = if (event.total.isNotEmpty()) null else "Total tidak boleh kosong",
            tanggalTransaksi = if (event.tanggalTransaksi.isNotEmpty()) null else "Tanggal Transaksi tidak boleh kosong",
            catatan = if (event.catatan.isNotEmpty()) null else "Catatan tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertPengeluaran() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pengeluaranRepository.insertPengeluaran(uiState.insertUiEvent.toPengeluaran())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        insertUiEvent = PengeluaranInsertEvent(),
                        isEntryValid = FormErrorStatePengeluaran()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}


data class PengeluaranInsertUiState(
    val insertUiEvent: PengeluaranInsertEvent = PengeluaranInsertEvent(),
    val isEntryValid: FormErrorStatePengeluaran = FormErrorStatePengeluaran(),
    val snackBarMessage: String? = null,
    val error: String? = null,
    val kategoriList: AllKategoriResponse = AllKategoriResponse(
        status = false,
        message = "No kategori data available",
        data = emptyList()
    ),
    val asetList: AllAsetResponse = AllAsetResponse(
        status = false,
        message = "No aset data available",
        data = emptyList()
    )
)

data class FormErrorStatePengeluaran(
    val total: String? = null,
    val idKategori: String? = null,
    val idAset: String? = null,
    val tanggalTransaksi: String? = null,
    val catatan: String? = null,
) {
    fun isValid(): Boolean {
        return total == null
                && idKategori == null
                && idAset == null
                && tanggalTransaksi == null
                && catatan == null
    }
}

data class PengeluaranInsertEvent(
    val id: String = "",
    val total: String = "",
    val idKategori: String = "",
    val idAset: String = "",
    val tanggalTransaksi: String = "",
    val catatan: String = ""
)

fun PengeluaranInsertEvent.toPengeluaran(): Pengeluaran {
    val idKategoriParsed = idKategori.toIntOrNull() ?: throw IllegalArgumentException("ID Kategori tidak valid")
    val idAsetParsed = idAset.toIntOrNull() ?: throw IllegalArgumentException("ID Aset tidak valid")

    return Pengeluaran(
        total = total.toFloat(),
        idKategori = idKategoriParsed,
        idAset = idAsetParsed,
        tanggalTransaksi = tanggalTransaksi,
        catatan = catatan
    )
}


