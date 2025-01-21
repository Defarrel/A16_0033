package com.example.gloycash.ui.viewmodel.aset

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Aset
import com.example.gloycash.repository.AsetRepository
import kotlinx.coroutines.launch

class AsetInsertViewModel(private val asetRepository: AsetRepository) : ViewModel() {
    var uiState by mutableStateOf(InsertUiState())
        private set
    var snackBarHostState = SnackbarHostState()
        private set

    fun updateInsertAsetState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(
            insertUiEvent = insertUiEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertUiEvent
        val errorState = FormErrorStateAset(
            namaAset = if (event.namaAset.isNotEmpty()) null else "Nama Aset tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun insertAset() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    asetRepository.insertAset(uiState.insertUiEvent.toAset())
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
    val namaAset: String? = null
) {
    fun isValid(): Boolean {
        return namaAset == null
    }
}

data class InsertUiEvent(
    val namaAset: String = ""
)

fun InsertUiEvent.toAset(): Aset = Aset(
    namaAset = namaAset
)