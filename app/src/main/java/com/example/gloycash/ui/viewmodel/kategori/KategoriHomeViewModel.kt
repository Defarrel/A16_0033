package com.example.gloycash.ui.viewmodel.kategori

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Kategori
import com.example.gloycash.repository.KategoriRepository
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed class KategoriUiState {
    data class Success(val Kategori: List<Kategori>) : KategoriUiState()
    object Loading : KategoriUiState()
    object Error : KategoriUiState()
}

class KategoriHomeViewModel(private val kategoriRepository: KategoriRepository) : ViewModel() {
    var uiState: KategoriUiState by mutableStateOf(KategoriUiState.Loading)
        private set

    init {
        getKategori()
    }

    fun getKategori() {
        viewModelScope.launch {
            uiState = KategoriUiState.Loading
            uiState = try {
                val response = kategoriRepository.getKategori()
                KategoriUiState.Success(response.data)
            } catch (e: IOException) {
                KategoriUiState.Error
            } catch (e: HttpException) {
                KategoriUiState.Error
            }
        }
    }
}
