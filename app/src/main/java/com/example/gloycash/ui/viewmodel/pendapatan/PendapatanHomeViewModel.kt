package com.example.gloycash.ui.viewmodel.pendapatan

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.repository.PengeluaranRepository
import com.example.gloycash.ui.viewmodel.home.HomeUiState
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class PendapatanUiState {
    data class Success(val totalPendapatan: Float, val totalPengeluaran: Float, val saldo: Float, val pendapatan: List<Pendapatan>) : PendapatanUiState()
    object Loading : PendapatanUiState()
    object Error : PendapatanUiState()
}

class PendapatanHomeViewModel(
    private val pendapatanRepository: PendapatanRepository,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {
    var uiState: PendapatanUiState by mutableStateOf(PendapatanUiState.Loading)
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

    fun getPendapatan() {
        viewModelScope.launch {
            uiState = PendapatanUiState.Loading
            uiState = try {
                val response = pendapatanRepository.getPendapatan()
                totalPendapatan = response.data.fold(0f) { acc, pendapatan ->
                    acc + pendapatan.total.toFloat()
                }
                calculateSaldo()
                PendapatanUiState.Success(totalPendapatan, totalPengeluaran, saldo, pendapatan = response.data)
            } catch (e: IOException) {
                PendapatanUiState.Error
            } catch (e: HttpException) {
                PendapatanUiState.Error
            }
        }
    }

    private fun getPengeluaran() {
        viewModelScope.launch {
            try {
                val response = pengeluaranRepository.getPengeluaran()
                totalPengeluaran = response.data.fold(0f) { acc, pengeluaran ->
                    acc + pengeluaran.total.toFloat()
                }
                calculateSaldo()
            } catch (e: IOException) {
                totalPengeluaran = 0f
            } catch (e: HttpException) {
                totalPengeluaran = 0f
            }
        }
    }

    private fun calculateSaldo() {
        saldo = totalPendapatan - totalPengeluaran
    }
}