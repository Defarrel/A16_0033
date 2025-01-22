package com.example.gloycash.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.repository.PengeluaranRepository
import com.example.gloycash.ui.navigation.DestinasiUpdatePengeluaran
import kotlinx.coroutines.launch
import toDetailUiEvent

class PengeluaranUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {
    var updateUIState by mutableStateOf(PengeluaranInsertUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiUpdatePengeluaran.ID)?.toInt())

    init {
        viewModelScope.launch {
            updateUIState = pengeluaranRepository.getPengeluaranById(_id)
                .toUIStatePengeluaran()
        }
    }

    fun updateState(pengeluaranInsertUiEvent: PengeluaranInsertEvent) {
        updateUIState = updateUIState.copy(insertUiEvent = pengeluaranInsertUiEvent)
    }

    suspend fun updatePengeluaran() {
        try {
            pengeluaranRepository.updatePengeluaran(
                id = _id,
                pengeluaran = updateUIState.insertUiEvent.toPengeluaran()
            )
        } catch (e: Exception) {
            updateUIState = updateUIState.copy(error = e.message)
        }
    }
}

fun Pengeluaran.toUIStatePengeluaran(): PengeluaranInsertUiState = PengeluaranInsertUiState(
    insertUiEvent = this.toDetailUiEvent(),
)
