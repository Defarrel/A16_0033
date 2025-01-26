package com.example.gloycash.ui.viewmodel.pendapatan

import InsertUiEvent
import InsertUiState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.ui.navigation.DestinasiUpdatePendapatan
import kotlinx.coroutines.launch
import toDetailUiEvent
import toPendapatan

class PendapatanUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository,
    private val kategoriRepository: KategoriRepository,
    private val asetRepository: AsetRepository
) : ViewModel() {
    var updateUIState by mutableStateOf(InsertUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiUpdatePendapatan.ID)?.toInt())

    init {
        loadKategoriDanAset()
        loadPendapatanDetail()
    }

    private fun loadKategoriDanAset() {
        viewModelScope.launch {
            val kategori = kategoriRepository.getKategori()
            val aset = asetRepository.getAset()
            updateUIState = updateUIState.copy(
                kategoriList = kategori,
                asetList = aset
            )
        }
    }

    private fun loadPendapatanDetail() {
        viewModelScope.launch {
            updateUIState = pendapatanRepository.getPendapatanById(_id)
                .toUIStatePendapatan(updateUIState.kategoriList, updateUIState.asetList)
        }
    }

    fun updateState(insertUiEvent: InsertUiEvent) {
        updateUIState = updateUIState.copy(insertUiEvent = insertUiEvent)
    }

    suspend fun updatePendapatan() {
        try {
            pendapatanRepository.updatePendapatan(
                id = _id,
                pendapatan = updateUIState.insertUiEvent.toPendapatan()
            )
        } catch (e: Exception) {
            updateUIState = updateUIState.copy(error = e.message)
        }
    }
}

fun Pendapatan.toUIStatePendapatan(
    kategoriList: AllKategoriResponse,
    asetList: AllAsetResponse
): InsertUiState = InsertUiState(
    insertUiEvent = this.toDetailUiEvent(kategoriList, asetList),
    kategoriList = kategoriList,
    asetList = asetList
)

fun Pendapatan.toDetailUiEvent(
    kategoriList: AllKategoriResponse,
    asetList: AllAsetResponse
): InsertUiEvent {
    val kategoriName = kategoriList.data?.find { it.id == this.idKategori }?.namaKategori ?: ""
    val asetName = asetList.data?.find { it.id == this.idAset }?.namaAset ?: ""

    return InsertUiEvent(
        id = id.toString(),
        total = total.toInt().toString(),
        idKategori = idKategori.toString(),
        idAset = idAset.toString(),
        tanggalTransaksi = tanggalTransaksi,
        catatan = catatan
    )
}
