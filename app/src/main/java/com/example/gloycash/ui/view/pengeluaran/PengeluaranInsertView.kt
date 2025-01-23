package com.example.gloycash.ui.view.pengeluaran

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.gloycash.ui.viewmodel.pengeluaran.FormErrorStatePengeluaran
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranInsertEvent
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranInsertUiState
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranInsertViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengeluaranInsertView(
    navigateBack: () -> Unit,
    navigateToPengeluaran: () -> Unit,
    navigateToPendapatan: () -> Unit,
    navigateToAset: () -> Unit,
    navigateToKategori: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PengeluaranInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                onBack = navigateBack,
                showBackButton = true,
                showProfile = true,
                showSaldo = false,
                showPageTitle = true,
                Judul = "Sudah Kah Anda Mengeluarkan Dana?",
                saldo = "",
                showRefreshButton = false,
                onRefresh = {},
            )
        },
        bottomBar = {
            BottomAppBar(
                showTambahClick = true,
                showFormAddClick = false,
                onPendapatanClick = navigateToPendapatan,
                onPengeluaranClick = navigateToPengeluaran,
                onAsetClick = navigateToAset,
                onKategoriClick = navigateToKategori,
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
                pengeluaranInsertUiState = viewModel.uiState,
                onPengeluaranValueChange = viewModel::updateInsertPengeluaranState,
                onSaveClick = { coroutineScope.launch { viewModel.insertPengeluaran() } },
                onViewAllClick = navigateToPengeluaran,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun EntryBody(
    pengeluaranInsertUiState: PengeluaranInsertUiState,
    onPengeluaranValueChange: (PengeluaranInsertEvent) -> Unit,
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
                    text = "Isilah Pengeluaran Anda",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.black)
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                FormInput(
                    insertUiEvent = pengeluaranInsertUiState.insertUiEvent,
                    onValueChange = onPengeluaranValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    kategoriList = pengeluaranInsertUiState.kategoriList,
                    asetList = pengeluaranInsertUiState.asetList,
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
    insertUiEvent: PengeluaranInsertEvent,
    modifier: Modifier = Modifier,
    onValueChange: (PengeluaranInsertEvent) -> Unit = {},
    kategoriList: AllKategoriResponse,
    asetList: AllAsetResponse,
    ErrorState: FormErrorStatePengeluaran = FormErrorStatePengeluaran(),
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
            text = ErrorState.idAset ?: "",
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
            placeholder = { Text("Masukkan Total Pengeluaran") },
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
            placeholder = { Text("Masukkan Catatan Pengeluaran") },
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
