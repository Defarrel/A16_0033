
import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.AsetRepository
import com.example.gloycash.repository.KategoriRepository
import com.example.gloycash.repository.PendapatanRepository
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.ui.viewmodel.pengeluaran.FormErrorStatePengeluaran
import kotlinx.coroutines.launch

class PendapatanInsertViewModel(
    private val pendapatanRepository: PendapatanRepository,
    private val kategoriRepository: KategoriRepository,
    private val asetRepository: AsetRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set
    var snackBarHostState = SnackbarHostState()
        private set

    init {
        loadKategoriDanAset()
    }

    fun loadKategoriDanAset() {
        viewModelScope.launch {
            val kategori = kategoriRepository.getKategori()
            val aset = asetRepository.getAset()
            uiState = uiState.copy(
                kategoriList = kategori,
                asetList = aset
            )
        }
    }

    fun updateInsertPendapatanState(insertUiEvent: InsertUiEvent) {
        uiState = uiState.copy(
            insertUiEvent = insertUiEvent
        )
    }

    private fun validateFields(): Boolean {
        val event = uiState.insertUiEvent
        val errorState = FormErrorStatePendapatan(
            idKategori = if (event.idKategori.toIntOrNull() != null) null else "Nama Kategori tidak valid",
            idAset = if (event.idAset.toIntOrNull() != null) null else "Nama Aset tidak valid",
            total = if (event.total.isNotEmpty()) null else "Total tidak boleh kosong",
            tanggalTransaksi = if (event.tanggalTransaksi.isNotEmpty()) null else "Tanggal Transaksi tidak boleh kosong",
            catatan = if (event.catatan.isNotEmpty()) null else "Catatan tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }


    fun insertPendapatan() {
        if (validateFields()) {
            viewModelScope.launch {
                try {
                    pendapatanRepository.insertPendapatan(uiState.insertUiEvent.toPendapatan())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        insertUiEvent = InsertUiEvent(),
                        isEntryValid = FormErrorStatePendapatan()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        } else {
            uiState = uiState.copy(
                snackBarMessage = "Input tidak valid. Periksa kembali data anda"
            )
        }
    }

    fun resetSnackBarMessage() {
        uiState = uiState.copy(snackBarMessage = null)
    }
}

data class InsertUiState(
    val insertUiEvent: InsertUiEvent = InsertUiEvent(),
    val isEntryValid: FormErrorStatePendapatan = FormErrorStatePendapatan(),
    val snackBarMessage: String? = null,
    val error: String? = null,
    val kategoriList: AllKategoriResponse = AllKategoriResponse(
        status = false,
        message = "No kategori data available",
        data = emptyList()
    ),
    val asetList: AllAsetResponse = AllAsetResponse(
        status = false,
        message = "No aset data available",
        data = emptyList()
    )
)


data class FormErrorStatePendapatan(
    val total: String? = null,
    val idKategori: String? = null,
    val idAset: String? = null,
    val tanggalTransaksi: String? = null,
    val catatan: String? = null,
) {
    fun isValid(): Boolean {
        return total == null
                && idKategori == null
                && idAset == null
                && tanggalTransaksi == null
                && catatan == null
    }
}

data class InsertUiEvent(
    val id: String = "",
    val total: String = "",
    val idKategori: String = "",
    val idAset: String = "",
    val tanggalTransaksi: String = "",
    val catatan: String = ""
)

fun InsertUiEvent.toPendapatan(): Pendapatan {
    val idKategoriParsed = idKategori.toIntOrNull() ?: throw IllegalArgumentException("ID Kategori tidak valid")
    val idAsetParsed = idAset.toIntOrNull() ?: throw IllegalArgumentException("ID Aset tidak valid")

    return Pendapatan(
        total = total.toFloat(),
        idKategori = idKategoriParsed,
        idAset = idAsetParsed,
        tanggalTransaksi = tanggalTransaksi,
        catatan = catatan
    )
}

