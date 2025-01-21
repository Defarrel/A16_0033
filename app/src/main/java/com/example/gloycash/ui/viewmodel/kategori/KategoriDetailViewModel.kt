package com.example.gloycash.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.gloycash.model.Kategori
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.ui.navigation.DestinasiDetailKategori
import kotlinx.coroutines.launch
import java.io.IOException

class KategoriDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val kategoriRepository: KategoriRepository
) : ViewModel() {
    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiDetailKategori.ID)?.toInt())

    var detailUiState: DetailUiState by mutableStateOf(value = DetailUiState(isLoading = true))

        private set

    init {
        getKategoriById()
    }

    private fun getKategoriById() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                val result = kategoriRepository.getKategoriById(_id)
                if (result != null) {
                    detailUiState = DetailUiState(
                        detailUiEvent = result.toDetailUiEvent(),
                        isLoading = false
                    )
                } else {
                    detailUiState = DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Data not found"
                    )
                }
            } catch (e: Exception) {
                detailUiState = DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun onDeleteClick() {
        deleteKategori(id = _id)
    }

    private fun deleteKategori(id: Int) {
        viewModelScope.launch {
            try {
                kategoriRepository.deleteKategori(id)
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = "Kategori deleted successfully"
                )
            } catch (e: IOException) {
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Network error occurred"
                )
            } catch (e: HttpException) {
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Server error occurred"
                )
            }
        }
    }
}

data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Kategori.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        namaKategori = namaKategori
    )
}
