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

    // Variable to store the total pendapatan
    var totalPendapatan: Float by mutableStateOf(0f)
        private set

    init {
        getPendapatan() // Get the list of pendapatan
        getTotalPendapatan() // Calculate the total pendapatan right away
    }

    // Fetch the pendapatan and update the UI state
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

    // Calculate and update the total pendapatan
    fun getTotalPendapatan() {
        viewModelScope.launch {
            try {
                val response = pendapatanRepository.getPendapatan()
                // Manually sum the 'total' values by iterating
                totalPendapatan = response.data.fold(0f) { acc, pendapatan ->
                    acc + pendapatan.total.toFloat()
                }
            } catch (e: IOException) {
                // Handle any exceptions related to the network or data fetch
                totalPendapatan = 0f
            } catch (e: HttpException) {
                // Handle any HTTP exceptions
                totalPendapatan = 0f
            }
        }
    }
}