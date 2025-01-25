package com.example.gloycash.ui.view.pengeluaran

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.model.Pengeluaran
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.view.aset.OnError
import com.example.gloycash.ui.view.aset.OnLoading
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import com.example.gloycash.ui.viewmodel.pendapatan.PendapatanUiState
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranHomeViewModel
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengeluaranHomeView(
    navigateToInsert: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToPendapatan: () -> Unit,
    navigateToPengeluaran: () -> Unit,
    navigateToAset: () -> Unit,
    navigateToKategori: () -> Unit,
    navigateToHome: () -> Unit,
    onDetailClick: (Int) -> Unit = {},
    viewModel: PengeluaranHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState
    val saldoColor = when {
        uiState is PengeluaranUiState.Success && uiState.totalPendapatan > uiState.totalPengeluaran ->
            colorResource(id = R.color.green)
        uiState is PengeluaranUiState.Success && uiState.totalPendapatan < uiState.totalPengeluaran ->
            Color.Red
        else -> colorResource(id = R.color.white)
    }
    var insertConfirmationRequired by rememberSaveable { mutableStateOf(false) }

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
                saldoColor = saldoColor,
                saldo = "${viewModel.saldo.toInt()}",
                onRefresh = viewModel::getPengeluaran
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
                onClick = {
                    if (viewModel.saldo < 0) {
                        insertConfirmationRequired = true
                    } else {
                        navigateToInsert()
                    }
                },
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
        PengeluaranStatus(
            pengeluaranUiState = viewModel.uiState,
            retryAction = { viewModel.getPengeluaran() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
    if (insertConfirmationRequired) {
        ConfirmationInsert(
            onInsertConfirm = {
                insertConfirmationRequired = false
                navigateToInsert()
            },
            onInsertCancel = { insertConfirmationRequired = false }
        )
    }
}


@Composable
fun PengeluaranStatus(
    pengeluaranUiState: PengeluaranUiState,
    retryAction: () -> Unit,
    modifier: Modifier,
    onDetailClick: (Int) -> Unit
) {
    when (pengeluaranUiState) {
        is PengeluaranUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is PengeluaranUiState.Success -> {
            if (pengeluaranUiState.pengeluaran.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Tidak ada data Pengeluaran")
                        Button(onClick = retryAction) {
                            Text(text = "Coba Lagi")
                        }
                    }
                }
            } else {
                PengeluaranLayout(
                    pengeluaranList = pengeluaranUiState.pengeluaran,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id) }
                )
            }
        }
        is PengeluaranUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun PengeluaranLayout(
    pengeluaranList: List<Pengeluaran>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pengeluaran) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pengeluaranList) { pengeluaran ->
            PengeluaranCard(
                pengeluaran = pengeluaran,
                modifier = Modifier.fillMaxWidth(),
                onDetailClick = { onDetailClick(pengeluaran) },
            )
        }
    }
}

@Composable
fun PengeluaranCard(
    pengeluaran: Pengeluaran,
    modifier: Modifier = Modifier,
    onDetailClick: (Pengeluaran) -> Unit
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
                    IconButton(
                        onClick = { onDetailClick(pengeluaran) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = colorResource(id = R.color.white),
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = pengeluaran.catatan,
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.white)
                    )
                    Text(
                        text = "Pengeluaran: Rp.${pengeluaran.total.toInt()}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}

@Composable
private fun ConfirmationInsert(
    onInsertConfirm: () -> Unit,
    onInsertCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {/* Do Nothing */},
        title = { Text("Insert Data") },
        text = { Text("”Keuangan anda sudah minus, apakah anda yakin menambah data\n" +
                "pengeluaran?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onInsertCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onInsertConfirm) {
                Text("Yes")
            }
        }
    )
}
