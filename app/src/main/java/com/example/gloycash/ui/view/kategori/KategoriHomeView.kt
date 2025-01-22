package com.example.gloycash.ui.view.kategori

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gloycash.R
import com.example.gloycash.model.Kategori
import com.example.gloycash.ui.costumwidget.BottomAppBar
import com.example.gloycash.ui.costumwidget.TopAppBar
import com.example.gloycash.ui.view.aset.OnError
import com.example.gloycash.ui.view.aset.OnLoading
import com.example.gloycash.ui.viewmodel.PenyediaViewModel
import com.example.gloycash.ui.viewmodel.aset.AsetUiState
import com.example.gloycash.ui.viewmodel.kategori.KategoriHomeViewModel
import com.example.gloycash.ui.viewmodel.kategori.KategoriUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KategoriHomeView(
    navigateToInsert: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    viewModel: KategoriHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                onBack = {},
                showBackButton = false,
                showProfile = true,
                showSaldo = false,
                showPageTitle = true,
                Judul = "Kategori Anda Saat Ini",
                saldo = "",
                showRefreshButton = true,
                onRefresh = viewModel::getKategori,
            )
        },
        bottomBar = {
            BottomAppBar(
                showTambahClick = true,
                showFormAddClick = true,
                onPendapatanClick = {},
                onPengeluaranClick = {},
                onAsetClick = {},
                onKategoriClick = {},
                onAdd = navigateToInsert,
            )
        },
    ) { innerPadding ->
        HomeStatus(
            homeUiState = viewModel.uiState,
            retryAction = { viewModel.getKategori() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick
        )
    }
}

@Composable
fun HomeStatus(
    homeUiState: KategoriUiState,
    retryAction: () -> Unit,
    modifier: Modifier,
    onDetailClick: (Int) -> Unit
) {
    when (homeUiState) {
        is KategoriUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is KategoriUiState.Success -> {
            if (homeUiState.Kategori.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Tidak ada data Kategori")
                        Button(onClick = retryAction) {
                            Text(text = "Coba Lagi")
                        }
                    }
                }
            } else {
                KategoriLayout(
                    kategoriList = homeUiState.Kategori,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id) }
                )
            }
        }
        is KategoriUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun KategoriLayout(
    kategoriList: List<Kategori>,
    modifier: Modifier = Modifier,
    onDetailClick: (Kategori) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(kategoriList) { kategori ->
            KategoriCard(
                kategori = kategori,
                modifier = Modifier.fillMaxWidth().clickable { onDetailClick(kategori) },
            )
        }
    }
}

@Composable
fun KategoriCard(
    kategori: Kategori,
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
                        text = kategori.namaKategori,  // ganti namaAset menjadi namaKategori
                        style = MaterialTheme.typography.titleMedium,
                        color = colorResource(id = R.color.white)
                    )
                }
            }
        }
    }
}
