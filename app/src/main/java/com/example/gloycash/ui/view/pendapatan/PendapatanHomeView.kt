package com.example.gloycash.ui.view.pendapatan

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.view.aset.OnError
import com.example.gloycash.ui.view.aset.OnLoading
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import com.example.gloycash.ui.viewmodel.pendapatan.PendapatanHomeViewModel
import com.example.gloycash.ui.viewmodel.pendapatan.PendapatanUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendapatanHomeView(
    navigateToInsert: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    navigateToPendapatan: () -> Unit,
    navigateToPengeluaran: () -> Unit,
    navigateToAset: () -> Unit,
    navigateToKategori: () -> Unit,
    navigateToHome: () -> Unit,
    viewModel: PendapatanHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val totalPendapatan = viewModel.totalPendapatan

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                onBack = {},
                showBackButton = false,
                showProfile = true,
                showSaldo = false,
                showPageTitle = true,
                Judul = "Total Pendapatan: Rp ${totalPendapatan}",
                saldo = "",
                onRefresh = viewModel::getPendapatan
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
                onAdd = navigateToHome,
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToInsert,
                containerColor = colorResource(id = R.color.warna3),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    tint = colorResource(id = R.color.white),
                    imageVector = Icons.Default.Add,
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding ->
        PendapatanStatus(
            pendapatanUiState = viewModel.uiState,
            retryAction = { viewModel.getPendapatan() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}


@Composable
fun PendapatanStatus(
    pendapatanUiState: PendapatanUiState,
    retryAction: () -> Unit,
    modifier: Modifier,
    onDetailClick: (Int) -> Unit
) {
    when (pendapatanUiState) {
        is PendapatanUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PendapatanUiState.Success -> {
            if (pendapatanUiState.pendapatan.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Tidak ada data Pendapatan")
                        Button(onClick = retryAction) {
                            Text(text = "Coba Lagi")
                        }
                    }
                }
            } else {
                PendapatanLayout(
                    pendapatanList = pendapatanUiState.pendapatan,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id) }
                )
            }
        }
        is PendapatanUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PendapatanLayout(
    pendapatanList: List<Pendapatan>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pendapatan) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pendapatanList) { pendapatan ->
            PendapatanCard(
                pendapatan = pendapatan,
                modifier = Modifier.fillMaxWidth().clickable { onDetailClick(pendapatan) },
            )
        }
    }
}

@Composable
fun PendapatanCard(
    pendapatan: Pendapatan,
    modifier: Modifier = Modifier,
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp, vertical = 4.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.warna2),
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .background(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = colorResource(id = R.color.white),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = pendapatan.tanggalTransaksi,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.white)
                    )
                    Text(
                        text = "Total: ${pendapatan.total}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}
