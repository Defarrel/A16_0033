package com.example.gloycash.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Aset
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.ui.navigation.DestinasiUpdateAset
import kotlinx.coroutines.launch

class AsetUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository: AsetRepository
) : ViewModel() {
    var updateUIState by mutableStateOf(InsertUiState())
        private set
    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiUpdateAset.ID)?.toInt())

    init{
        viewModelScope.launch {
            updateUIState = asetRepository.getAsetById(_id)
                .toUIStateMhs()
        }
    }
    fun updateState(insertUiEvent: InsertUiEvent) {
        updateUIState = updateUIState.copy(insertUiEvent = insertUiEvent)
    }

    suspend fun updateAset() {
        try {
            asetRepository.updateAset(
                id = _id,
                aset = updateUIState.insertUiEvent.toAset()
            )
        } catch (e: Exception) {
            updateUIState = updateUIState.copy(error = e.message)
        }
    }
}

fun Aset.toUIStateMhs(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent(),
)