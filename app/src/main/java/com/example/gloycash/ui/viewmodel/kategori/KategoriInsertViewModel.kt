package com.example.gloycash.ui.viewmodel.kategori

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Kategori
import com.example.gloycash.repository.KategoriRepository
import kotlinx.coroutines.launch

class KategoriInsertViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set
    var snackBarHostState = SnackbarHostState()
        private set

    fun updateInsertKategoriState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(
            insertUiEvent = insertUiEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertUiEvent
        val errorState = FormErrorStateAset(
            namaKategori = if (event.namaKategori.isNotEmpty()) null else "Nama Kategori tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertKategori() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    kategoriRepository.insertKategori(uiState.insertUiEvent.toKategori())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        insertUiEvent = InsertUiEvent(),
                        isEntryValid = FormErrorStateAset()
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

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: FormErrorStateAset = FormErrorStateAset(),
    val snackBarMessage: String? = null,
    val error: String? = null
)

data class FormErrorStateAset(
    val namaKategori: String? = null
) {
    fun isValid(): Boolean {
        return namaKategori == null
    }
}

data class InsertUiEvent(
    val namaKategori: String = ""
)

fun InsertUiEvent.toKategori(): Kategori = Kategori(
    namaKategori = namaKategori
)
