package com.example.gloycash.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.repository.PengeluaranRepository
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class PengeluaranUiState {
    data class Success(val pengeluaran: List<Pengeluaran>) : PengeluaranUiState()
    object Loading : PengeluaranUiState()
    object Error : PengeluaranUiState()
}

class PengeluaranHomeViewModel(private val pengeluaranRepository: PengeluaranRepository) : ViewModel() {
    var uiState: PengeluaranUiState by mutableStateOf(PengeluaranUiState.Loading)
        private set

    var totalPengeluaran: Float by mutableStateOf(0f)
        private set

    init {
        getPengeluaran()
        getTotalPengeluaran()
    }

    fun getPengeluaran() {
        viewModelScope.launch {
            uiState = PengeluaranUiState.Loading
            uiState = try {
                val response = pengeluaranRepository.getPengeluaran()
                PengeluaranUiState.Success(response.data)
            } catch (e: IOException) {
                PengeluaranUiState.Error
            } catch (e: HttpException) {
                PengeluaranUiState.Error
            }
        }
    }

    fun getTotalPengeluaran() {
        viewModelScope.launch {
            try {
                val response = pengeluaranRepository.getPengeluaran()
                totalPengeluaran = response.data.fold(0f) { acc, pengeluaran ->
                    acc + pengeluaran.total.toFloat()
                }
            } catch (e: IOException) {
                totalPengeluaran = 0f
            } catch (e: HttpException) {
                totalPengeluaran = 0f
            }
        }
    }
}
