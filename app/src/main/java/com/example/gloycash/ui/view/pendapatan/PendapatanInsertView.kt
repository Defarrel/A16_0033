import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.model.AllAsetResponse
import com.example.gloycash.model.AllKategoriResponse
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.DynamicSelectField
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendapatanInsertView(
    navigateBack: () -> Unit,
    navigateToPendapatan: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PendapatanInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val snackBarHostState = remember { viewModel.snackBarHostState }

    LaunchedEffect(viewModel.uiState.snackBarMessage) {
        viewModel.uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackBarHostState.showSnackbar(message)
                viewModel.resetSnackBarMessage()
            }
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                onBack = {},
                showBackButton = false,
                showProfile = true,
                showSaldo = false,
                showPageTitle = true,
                Judul = "Sudah Kah Anda Menabung?",
                saldo = "",
                pendapatan = "",
                pengeluaran = "",
                saldoColor = MaterialTheme.colorScheme.primary,
            )
        },
        bottomBar = {
            BottomAppBar(
                showTambahClick = true,
                showFormAddClick = false,
                onPendapatanClick = {},
                onPengeluaranClick = {},
                onAsetClick = {},
                onKategoriClick = {},
                onAdd = navigateBack,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            EntryBody(
                insertUiState = viewModel.uiState,
                onPendapatanValueChange = viewModel::updateInsertPendapatanState,
                onSaveClick = { coroutineScope.launch { viewModel.insertPendapatan() } },
                onViewAllClick = navigateToPendapatan,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}



@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onPendapatanValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    onViewAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = "Isilah Pendapatan Anda",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.black)
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormInput(
                    insertUiEvent = insertUiState.insertUiEvent,
                    onValueChange = onPendapatanValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    kategoriList = insertUiState.kategoriList,
                    asetList = insertUiState.asetList,
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onSaveClick,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.warna2),
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Simpan",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = onViewAllClick,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.warna2),
                    ),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Lihat Data",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    kategoriList: AllKategoriResponse,
    asetList: AllAsetResponse,
    ErrorState: FormErrorStatePendapatan = FormErrorStatePendapatan(),
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
    ) {
        DynamicSelectField(
            selectedValue = insertUiEvent.idKategori,
            options = kategoriList.data?.map { it.namaKategori } ?: emptyList(),
            label = "Nama Kategori",
            placeholder = "Pilih Kategori Anda",
            onValueChangedEvent = {
                onValueChange(insertUiEvent.copy(idKategori = it))
            },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(12.dp))

        DynamicSelectField(
            selectedValue = insertUiEvent.idAset,
            options = asetList.data?.map { it.namaAset } ?: emptyList(),
            label = "Nama Aset",
            placeholder = "Pilih Aset Anda",
            onValueChangedEvent = {
                onValueChange(insertUiEvent.copy(idAset = it))
            },
            modifier = Modifier.fillMaxWidth(),
        )
        Text(
            text = ErrorState. idAset?: "",
            color = Color.Red
        )
        OutlinedTextField(
            value = insertUiEvent.tanggalTransaksi,
            onValueChange = { onValueChange(insertUiEvent.copy(tanggalTransaksi = it)) },
            isError = ErrorState.tanggalTransaksi != null,
            label = { Text("Tanggal Transaksi", color = colorResource(id = R.color.black)) },
            placeholder = { Text("Masukkan Tanggal Transaksi") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.warna2),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(25.dp)
        )
        Text(
            text = ErrorState.tanggalTransaksi ?: "",
            color = Color.Red,
        )
        OutlinedTextField(
            value = insertUiEvent.total,
            onValueChange = { onValueChange(insertUiEvent.copy(total = it)) },
            isError = ErrorState.total != null,
            label = { Text("Total", color = colorResource(id = R.color.black)) },
            placeholder = { Text("Masukkan Total Pendapatan") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.warna2),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(25.dp)
        )
        Text(
            text = ErrorState.total ?: "",
            color = Color.Red,
        )
        OutlinedTextField(
            value = insertUiEvent.catatan,
            onValueChange = { onValueChange(insertUiEvent.copy(catatan = it)) },
            isError = ErrorState.catatan != null,
            label = { Text("Catatan", color = colorResource(id = R.color.black)) },
            placeholder = { Text("Masukkan Total Catatan") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.warna2),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape(25.dp)
        )
        Text(
            text = ErrorState.catatan ?: "",
            color = Color.Red,
        )
    }
}
