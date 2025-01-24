package com.example.gloycash.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.repository.PengeluaranRepository
import com.example.gloycash.ui.viewmodel.pendapatan.PendapatanUiState
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class PengeluaranUiState {
    data class Success(val totalPendapatan: Float, val totalPengeluaran: Float, val saldo: Float, val pengeluaran: List<Pengeluaran>) : PengeluaranUiState()
    object Loading : PengeluaranUiState()
    object Error : PengeluaranUiState()
}

class PengeluaranHomeViewModel(
    private val pengeluaranRepository: PengeluaranRepository,
    private val pendapatanRepository: PendapatanRepository
) : ViewModel() {
    var uiState: PengeluaranUiState by mutableStateOf(PengeluaranUiState.Loading)
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

    fun getPengeluaran() {
        viewModelScope.launch {
            uiState = PengeluaranUiState.Loading
            uiState = try {
                val response = pengeluaranRepository.getPengeluaran()
                totalPengeluaran= response.data.fold(0f) { acc, pengeluaran ->
                    acc + pengeluaran.total.toFloat()
                }
                calculateSaldo()
                PengeluaranUiState.Success(totalPendapatan, totalPengeluaran, saldo, pengeluaran = response.data)
            } catch (e: IOException) {
                PengeluaranUiState.Error
            } catch (e: HttpException) {
                PengeluaranUiState.Error
            }
        }
    }

    fun getPendapatan() {
        viewModelScope.launch {
            try {
                val response = pendapatanRepository.getPendapatan()
                totalPendapatan = response.data.fold(0f) { acc, pendapatan ->
                    acc + pendapatan.total.toFloat()
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
