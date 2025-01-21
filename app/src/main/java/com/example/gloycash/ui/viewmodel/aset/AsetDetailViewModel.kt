package com.example.gloycash.ui.viewmodel.aset

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Aset
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.ui.navigation.DestinasiDetailAset
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class AsetDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val asetRepository: AsetRepository
) : ViewModel() {
    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiDetailAset.ID)?.toInt())

    var detailUiState: DetailUiState by mutableStateOf(DetailUiState(isLoading = true))

        private set

    init {
        getAsetbyId()
    }

    private fun getAsetbyId() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                val result = asetRepository.getAsetById(_id)
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
        deleteAset(id = _id)
    }

    private fun deleteAset(id: Int) {
        viewModelScope.launch {
            try {
                asetRepository.deleteAset(id)
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = "Asset deleted successfully"
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
){
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Aset.toDetailUiEvent(): InsertUiEvent{
    return InsertUiEvent(
        namaAset = namaAset
    )
}