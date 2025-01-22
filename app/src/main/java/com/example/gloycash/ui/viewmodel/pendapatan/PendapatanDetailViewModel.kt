import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.repository.PendapatanRepository
import com.example.gloycash.ui.navigation.DestinasiDetailPendapatan
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class PendapatanDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val pendapatanRepository: PendapatanRepository
) : ViewModel() {
    private val pendapatanId: Int = checkNotNull(savedStateHandle.get<String>(DestinasiDetailPendapatan.ID)?.toInt())

    var detailUiState: DetailUiState by mutableStateOf(value = DetailUiState(isLoading = true))
        private set

    init {
        getPendapatanById()
    }

    private fun getPendapatanById() {
        viewModelScope.launch {
            detailUiState = DetailUiState(isLoading = true)
            try {
                val result = pendapatanRepository.getPendapatanById(pendapatanId)
                if (result != null) {
                    detailUiState = DetailUiState(
                        detailUiEvent = result.toDetailUiEvent(),
                        isLoading = false
                    )
                } else {
                    detailUiState = DetailUiState(
                        isLoading = false,
                        isError = true,
                        errorMessage = "Data not found"
                    )
                }
            } catch (e: Exception) {
                detailUiState = DetailUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }

    fun onDeleteClick() {
        deletePendapatan(id = pendapatanId)
    }

    private fun deletePendapatan(id: Int) {
        viewModelScope.launch {
            try {
                pendapatanRepository.deletePendapatan(id)
                detailUiState = detailUiState.copy(
                    isLoading = false,
                    isError = false,
                    errorMessage = "Pendapatan deleted successfully"
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

data class DetailUiState(
    val detailUiEvent: InsertUiEvent = InsertUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == InsertUiEvent()

    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != InsertUiEvent()
}

fun Pendapatan.toDetailUiEvent(): InsertUiEvent {
    return InsertUiEvent(
        total = total.toString(),
        idKategori = idKategori.toString(),
        idAset = idAset.toString(),
        tanggalTransaksi = tanggalTransaksi,
        catatan = catatan
    )
}
