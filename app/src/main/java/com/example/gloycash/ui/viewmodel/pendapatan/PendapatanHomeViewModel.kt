package com.example.gloycash.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.PendapatanRepository
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class PendapatanUiState {
    data class Success(val pendapatan: List<Pendapatan>) : PendapatanUiState()
    object Loading : PendapatanUiState()
    object Error : PendapatanUiState()
}

class PendapatanHomeViewModel(private val pendapatanRepository: PendapatanRepository) : ViewModel() {
    var uiState: PendapatanUiState by mutableStateOf(PendapatanUiState.Loading)
        private set

    var totalPendapatan: Float by mutableStateOf(0f)
        private set

    init {
        getPendapatan()
        getTotalPendapatan()
    }

    fun getPendapatan() {
        viewModelScope.launch {
            uiState = PendapatanUiState.Loading
            uiState = try {
                val response = pendapatanRepository.getPendapatan()
                PendapatanUiState.Success(response.data)
            } catch (e: IOException) {
                PendapatanUiState.Error
            } catch (e: HttpException) {
                PendapatanUiState.Error
            }
        }
    }

    fun getTotalPendapatan() {
        viewModelScope.launch {
            try {
                val response = pendapatanRepository.getPendapatan()
                totalPendapatan = response.data.fold(0f) { acc, pendapatan ->
                    acc + pendapatan.total.toFloat()
                }
            } catch (e: IOException) {
                totalPendapatan = 0f
            } catch (e: HttpException) {
                totalPendapatan = 0f
            }
        }
    }
}