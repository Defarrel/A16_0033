package com.example.gloycash.ui.view.pengeluaran

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
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranUpdateViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengeluaranUpdateView(
    navigateBack: () -> Unit,
    navigateToPendapatan: () -> Unit,
    navigateToPengeluaran: () -> Unit,
    navigateToAset: () -> Unit,
    navigateToKategori: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PengeluaranUpdateViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val updateUiState = viewModel.updateUIState
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
                Judul = "Update Pengeluaran",
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
    ) { innerPadding ->
        EntryBody(
            pengeluaranInsertUiState = updateUiState,
            onPengeluaranValueChange = { viewModel.updateState(it) },
            onViewAllClick = {},
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePengeluaran()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}