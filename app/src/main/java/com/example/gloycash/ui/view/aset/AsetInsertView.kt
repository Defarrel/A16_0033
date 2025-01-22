package com.example.gloycash.ui.view.aset

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.navigation.DestinasiInsertAset
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import com.example.gloycash.ui.viewmodel.aset.AsetInsertViewModel
import com.example.gloycash.ui.viewmodel.aset.FormErrorStateAset
import com.example.gloycash.ui.viewmodel.aset.InsertUiEvent
import com.example.gloycash.ui.viewmodel.aset.InsertUiState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsetInsertView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AsetInsertViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                showProfile = false,
                showSaldo = false,
                showPageTitle = true,
                Judul = "Masukan Aset Baru",
                saldo = "",
                showRefreshButton = false,
                onRefresh = {},
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
        EntryBody(
            insertUiState = viewModel.uiState,
            onAsetValueChange = viewModel::updateInsertAsetState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertAset()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertUiState: InsertUiState,
    onAsetValueChange: (InsertUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            insertUiEvent = insertUiState.insertUiEvent,
            onValueChange = onAsetValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(id = R.color.warna2),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
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
    }
}


@Composable
fun FormInput(
    insertUiEvent: InsertUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertUiEvent) -> Unit = {},
    ErrorState: FormErrorStateAset = FormErrorStateAset(),
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = insertUiEvent.namaAset,
            onValueChange = {
                onValueChange(insertUiEvent.copy(namaAset = it)) },
            isError = ErrorState.namaAset != null,
            label = { Text("Nama Aset",modifier = Modifier, color = colorResource(id = R.color.black)) },
            placeholder = { Text("Masukkan Nama Aset") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.warna2),
                focusedTextColor = colorResource(id = R.color.black),
                unfocusedTextColor = colorResource(id = R.color.black),
                cursorColor = colorResource(id = R.color.black)
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = RoundedCornerShape((25.dp))
        )
        Text(
            text = ErrorState.namaAset ?: "",
            color = Color.Red,
            modifier = Modifier.padding(4.dp)
        )
    }
}
