import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.model.Pendapatan
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.viewmodel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PendapatanDetailView(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    onNavigateToHome: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PendapatanDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
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
                Judul = "Detail Pendapatan",
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
                onAdd = navigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEdit,
                containerColor = colorResource(id = R.color.warna3),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    tint = colorResource(id = R.color.white),
                    imageVector = Icons.Default.Edit,
                    contentDescription = ""
                )
            }
        }
    ) { innerPadding ->
        BodyDetailPendapatan(
            detailUiState = viewModel.detailUiState,
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                viewModel.onDeleteClick()
                onNavigateToHome()
            }
        )
    }
}

@Composable
fun BodyDetailPendapatan(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pendapatan) -> Unit = {}
) {
    when {
        detailUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailUiState.errorMessage,
                    color = Color.Red
                )
            }
        }
        detailUiState.isUiEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailPendapatan(
                    pendapatan = detailUiState.detailUiEvent.toPendapatan(),
                    modifier = modifier,
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
    }
}

@Composable
fun ItemDetailPendapatan(
    modifier: Modifier = Modifier,
    pendapatan: Pendapatan,
    onDeleteClick: (Pendapatan) -> Unit = {}
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 0.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Pendapatan: ${pendapatan.total}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.black)
                )
                IconButton(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete pendapatan",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = colorResource(id = R.color.black), thickness = 1.dp)

            ComponentDetailPendapatan(
                icon = Icons.Filled.Info,
                judul = "ID Kategori",
                isinya = pendapatan.idKategori.toString(),
            )
            ComponentDetailPendapatan(
                icon = Icons.Filled.Info,
                judul = "ID Aset",
                isinya = pendapatan.idAset.toString(),
            )
            ComponentDetailPendapatan(
                icon = Icons.Filled.DateRange,
                judul = "Tanggal Transaksi",
                isinya = pendapatan.tanggalTransaksi,
            )
            ComponentDetailPendapatan(
                icon = Icons.Filled.Info,
                judul = "Catatan",
                isinya = pendapatan.catatan,
            )
        }
    }

    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                onDeleteClick(pendapatan)
                deleteConfirmationRequired = false
            },
            onDeleteCancel = { deleteConfirmationRequired = false }
        )
    }
}

@Composable
fun ComponentDetailPendapatan(
    icon: ImageVector,
    judul: String,
    isinya: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.black),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = judul,
                fontSize = 12.sp,
                color = colorResource(id = R.color.black)
            )
            Text(
                text = isinya,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.black)
            )
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = {/* Do Nothing */},
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Yes")
            }
        }
    )
}
