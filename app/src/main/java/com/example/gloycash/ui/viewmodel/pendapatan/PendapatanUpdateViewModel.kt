package com.example.gloycash.ui.viewmodel.pendapatan

import InsertUiEvent
import InsertUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.ui.navigation.DestinasiUpdatePendapatan
import kotlinx.coroutines.launch
import toDetailUiEvent
import toPendapatan

class PendapatanUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository
) : ViewModel() {
    var updateUIState by mutableStateOf(InsertUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiUpdatePendapatan.ID)?.toInt())

    init {
        viewModelScope.launch {
            updateUIState = pendapatanRepository.getPendapatanById(_id)
                .toUIStatePendapatan()
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent) {
        updateUIState = updateUIState.copy(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePendapatan() {
        try {
            pendapatanRepository.updatePendapatan(
                id = _id,
                pendapatan = updateUIState.insertUiEvent.toPendapatan()
            )
        } catch (e: Exception) {
            updateUIState = updateUIState.copy(error = e.message)
        }
    }
}

fun Pendapatan.toUIStatePendapatan(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent(),
)
