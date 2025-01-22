package com.example.gloycash.ui.view.kategori

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import com.example.gloycash.ui.viewmodel.kategori.KategoriUpdateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KategoriUpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KategoriUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val insertUiState = viewModel.updateUIState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                onBack = navigateBack,
                showBackButton = true,
                showProfile = false,
                showSaldo = false,
                showPageTitle = true,
                Judul = "Update Kategori",
                saldo = "",
                pendapatan = "",
                pengeluaran = "",
                saldoColor = MaterialTheme.colorScheme.primary,
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
    ) { innerPadding ->
        EntryBody(
            insertUiState = insertUiState,
            onKategoriValueChange = { viewModel.updateState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKategori()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
