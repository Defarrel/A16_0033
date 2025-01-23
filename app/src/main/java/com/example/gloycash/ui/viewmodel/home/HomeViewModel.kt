package com.example.gloycash.ui.viewmodel.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.repository.KeuanganRepository
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.repository.PengeluaranRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val totalPendapatan: Float, val totalPengeluaran: Float, val saldo: Float) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

class HomeViewModel(
    private val pendapatanRepository: PendapatanRepository,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    var totalPendapatan: Float by mutableStateOf(0f)
        private set

    var totalPengeluaran: Float by mutableStateOf(0f)
        private set

    var saldo: Float by mutableStateOf(0f)
        private set

    init {
        getPendapatan()
        getPengeluaran()
    }

    private fun getPendapatan() {
        viewModelScope.launch {
            try {
                val response = pendapatanRepository.getPendapatan()
                totalPendapatan = response.data.fold(0f) { acc, pendapatan -> acc + pendapatan.total.toFloat() }
                calculateSaldo()
                uiState = HomeUiState.Success(totalPendapatan, totalPengeluaran, saldo)
            } catch (e: IOException) {
                uiState = HomeUiState.Error("Network error")
            } catch (e: HttpException) {
                uiState = HomeUiState.Error("Server error")
            }
        }
    }

    private fun getPengeluaran() {
        viewModelScope.launch {
            try {
                val response = pengeluaranRepository.getPengeluaran()
                totalPengeluaran = response.data.fold(0f) { acc, pengeluaran -> acc + pengeluaran.total.toFloat() }
                calculateSaldo()
                uiState = HomeUiState.Success(totalPendapatan, totalPengeluaran, saldo)
            } catch (e: IOException) {
                uiState = HomeUiState.Error("Network error")
            } catch (e: HttpException) {
                uiState = HomeUiState.Error("Server error")
            }
        }
    }

    private fun calculateSaldo() {
        saldo = totalPendapatan - totalPengeluaran
    }
}



