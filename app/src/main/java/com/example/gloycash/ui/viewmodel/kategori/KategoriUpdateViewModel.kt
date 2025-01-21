package com.example.gloycash.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Kategori
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.ui.navigation.DestinasiUpdateKategori
import kotlinx.coroutines.launch

class KategoriUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {
    var updateUIState by mutableStateOf(InsertUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiUpdateKategori.ID)?.toInt())

    init {
        viewModelScope.launch {
            updateUIState = kategoriRepository.getKategoriById(_id)
                .toUIStateKategori()
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent) {
        updateUIState = updateUIState.copy(insertUiEvent = insertUiEvent)
    }

    suspend fun updateKategori() {
        try {
            kategoriRepository.updateKategori(
                id = _id,
                kategori = updateUIState.insertUiEvent.toKategori()
            )
        } catch (e: Exception) {
            updateUIState = updateUIState.copy(error = e.message)
        }
    }
}

fun Kategori.toUIStateKategori(): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent(),
)
