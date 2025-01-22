import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.repository.PengeluaranRepository
import com.example.gloycash.ui.navigation.DestinasiDetailPengeluaran
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranInsertEvent
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PengeluaranDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengeluaranRepository: PengeluaranRepository
) : ViewModel() {
    private val pengeluaranId: Int = checkNotNull(savedStateHandle.get<String>(DestinasiDetailPengeluaran.ID)?.toInt())

    var detailUiState: PengeluaranDetailUiState by mutableStateOf(value = PengeluaranDetailUiState(isLoading = true))
        private set

    init {
        getPengeluaranById()
    }

    private fun getPengeluaranById() {
        viewModelScope.launch {
            detailUiState = PengeluaranDetailUiState(isLoading = true)
            try {
                val result = pengeluaranRepository.getPengeluaranById(pengeluaranId)
                if (result != null) {
                    detailUiState = PengeluaranDetailUiState(
                        detailUiEvent = result.toDetailUiEvent(),
                        isLoading = false
                    )
                } else {
                    detailUiState = PengeluaranDetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Data not found"
                    )
                }
            } catch (e: Exception) {
                detailUiState = PengeluaranDetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun onDeleteClick() {
        deletePengeluaran(id = pengeluaranId)
    }

    private fun deletePengeluaran(id: Int) {
        viewModelScope.launch {
            try {
                pengeluaranRepository.deletePengeluaran(id)
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = "Pengeluaran deleted successfully"
                )
            } catch (e: IOException) {
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Network error occurred"
                )
            } catch (e: HttpException) {
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = true,
                    errorMessage = "Server error occurred"
                )
            }
        }
    }
}

data class PengeluaranDetailUiState(
    val detailUiEvent: PengeluaranInsertEvent = PengeluaranInsertEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == PengeluaranInsertEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != PengeluaranInsertEvent()
}

fun Pengeluaran.toDetailUiEvent(): PengeluaranInsertEvent {
    return PengeluaranInsertEvent(
        total = total.toString(),
        idKategori = idKategori.toString(),
        idAset = idAset.toString(),
        tanggalTransaksi = tanggalTransaksi,
        catatan = catatan
    )
}
