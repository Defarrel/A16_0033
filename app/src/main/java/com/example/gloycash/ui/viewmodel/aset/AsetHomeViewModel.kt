package com.example.gloycash.ui.viewmodel.aset

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Aset
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.repository.PendapatanRepository
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class AsetUiState {
    data class Success(val Aset: List<Aset>) : AsetUiState()
    object Loading : AsetUiState()
    object Error : AsetUiState()
}

class AsetHomeViewModel(private val asetRepository: AsetRepository) : ViewModel() {
    var uiState: AsetUiState by mutableStateOf(AsetUiState.Loading)
        private set

    init {
        getAset()
    }

    fun getAset() {
        viewModelScope.launch {
            uiState = AsetUiState.Loading
            uiState = try {
                val response = asetRepository.getAset()
                AsetUiState.Success(response.data)
            } catch (e: IOException) {
                AsetUiState.Error
            } catch (e: HttpException) {
                AsetUiState.Error
            }
        }
    }
}
