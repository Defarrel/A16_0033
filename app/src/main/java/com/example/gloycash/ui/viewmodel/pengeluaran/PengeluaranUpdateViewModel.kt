package com.example.gloycash.ui.viewmodel.pengeluaran

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.repository.PengeluaranRepository
import com.example.gloycash.ui.navigation.DestinasiUpdatePengeluaran
import com.example.gloycash.ui.viewmodel.pendapatan.toUIStatePendapatan
import kotlinx.coroutines.launch
import toDetailUiEvent

class PengeluaranUpdateViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository,
    private val kategoriRepository: KategoriRepository,
    private val asetRepository: AsetRepository
) : ViewModel() {
    var updateUIState by mutableStateOf(PengeluaranInsertUiState())
        private set

    private val _id: Int = checkNotNull(savedStateHandle.get<String>(DestinasiUpdatePengeluaran.ID)?.toInt())

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
            updateUIState = pengeluaranRepository.getPengeluaranById(_id)
                .toUIStatePengeluaran(updateUIState.kategoriList, updateUIState.asetList)
        }
    }

    fun updateState(pengeluaranInsertUiEvent: PengeluaranInsertEvent) {
        updateUIState = updateUIState.copy(insertUiEvent = pengeluaranInsertUiEvent)
    }

    suspend fun updatePengeluaran() {
        try {
            pengeluaranRepository.updatePengeluaran(
                id = _id,
                pengeluaran = updateUIState.insertUiEvent.toPengeluaran()
            )
        } catch (e: Exception) {
            updateUIState = updateUIState.copy(error = e.message)
        }
    }
}

fun Pengeluaran.toUIStatePengeluaran(
    kategoriList: AllKategoriResponse,
    asetList: AllAsetResponse
): PengeluaranInsertUiState = PengeluaranInsertUiState(
    insertUiEvent = this.toDetailUiEvent(kategoriList, asetList),
    kategoriList = kategoriList,
    asetList = asetList
)

fun Pengeluaran.toDetailUiEvent(
    kategoriList: AllKategoriResponse,
    asetList: AllAsetResponse
): PengeluaranInsertEvent {
    val kategoriName = kategoriList.data?.find { it.id == this.idKategori }?.namaKategori ?: ""
    val asetName = asetList.data?.find { it.id == this.idAset }?.namaAset ?: ""

    return PengeluaranInsertEvent(
        id = id.toString(),
        total = total.toInt().toString(),
        idKategori = idKategori.toString(),
        idAset = idAset.toString(),
        tanggalTransaksi = tanggalTransaksi,
        catatan = catatan
    )
}
