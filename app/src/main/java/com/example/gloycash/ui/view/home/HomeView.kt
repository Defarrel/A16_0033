package com.example.gloycash.ui.view.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import com.example.gloycash.ui.viewmodel.home.HomeUiState
import com.example.gloycash.ui.viewmodel.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navigateToInsert: () -> Unit,
    navigateToPendapatan: () -> Unit,
    navigateToPengeluaran: () -> Unit,
    navigateToAset: () -> Unit,
    navigateToKategori: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState
    val saldoColor = when {
        uiState is HomeUiState.Success && uiState.totalPendapatan > uiState.totalPengeluaran ->
            colorResource(id = R.color.green)
        uiState is HomeUiState.Success && uiState.totalPendapatan < uiState.totalPengeluaran ->
            Color.Red
        else -> colorResource(id = R.color.white)
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                onBack = {},
                showBackButton = false,
                showProfile = true,
                showSaldo = true,
                showPageTitle = false,
                Judul = "",
                saldo = "${viewModel.saldo.toInt()}",
                saldoColor = saldoColor,
                showRefreshButton = false,
                onRefresh = {}
            )
        },
        bottomBar = {
            BottomAppBar(
                showTambahClick = true,
                showFormAddClick = true,
                onPendapatanClick = navigateToPendapatan,
                onPengeluaranClick = navigateToPengeluaran,
                onAsetClick = navigateToAset,
                onKategoriClick = navigateToKategori,
                onAdd = navigateToInsert
            )
        }
    ) { innerPadding ->
        HomeContentColumn(
            uiState = uiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeContentColumn(
    uiState: HomeUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        when (uiState) {
            is HomeUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(50.dp)
                )
            }
            is HomeUiState.Success -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.warna2)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(95.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Pendapatan",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colorResource(id = R.color.white)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Rp. ${uiState.totalPendapatan.toInt()}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colorResource(id = R.color.white),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(id = R.color.warna2)
                        )
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(95.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Pengeluaran",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colorResource(id = R.color.white)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Rp. ${uiState.totalPengeluaran.toInt()}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = colorResource(id = R.color.white),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
            is HomeUiState.Error -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = colorResource(id = R.color.warna2)
                    )
                ) {
                    Text(
                        text = "Error: ${uiState.message}",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(16.dp),
                        color = Color.Red
                    )
                }
            }
        }
    }
}



