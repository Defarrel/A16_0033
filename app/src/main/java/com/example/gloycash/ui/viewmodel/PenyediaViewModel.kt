package com.example.gloycash.ui.viewmodel

import PendapatanDetailViewModel
import PendapatanInsertViewModel
import PengeluaranDetailViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.gloycash.GloyCash
import com.example.gloycash.ui.view.aset.AsetDetailView
import com.example.gloycash.ui.view.aset.AsetHomeView
import com.example.gloycash.ui.view.kategori.KategoriHomeView
import com.example.gloycash.ui.view.kategori.KategoriUpdateView
import com.example.gloycash.ui.viewmodel.aset.AsetDetailViewModel
import com.example.gloycash.ui.viewmodel.aset.AsetHomeViewModel
import com.example.gloycash.ui.viewmodel.aset.AsetInsertViewModel
import com.example.gloycash.ui.viewmodel.aset.AsetUpdateViewModel
import com.example.gloycash.ui.viewmodel.home.HomeViewModel
import com.example.gloycash.ui.viewmodel.kategori.KategoriDetailViewModel
import com.example.gloycash.ui.viewmodel.kategori.KategoriHomeViewModel
import com.example.gloycash.ui.viewmodel.kategori.KategoriInsertViewModel
import com.example.gloycash.ui.viewmodel.kategori.KategoriUpdateViewModel
import com.example.gloycash.ui.viewmodel.pendapatan.PendapatanHomeViewModel
import com.example.gloycash.ui.viewmodel.pendapatan.PendapatanUpdateViewModel
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranHomeViewModel
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranInsertEvent
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranInsertViewModel
import com.example.gloycash.ui.viewmodel.pengeluaran.PengeluaranUpdateViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        //home
        initializer {
            HomeViewModel(
                GloyCash().container.pendapatanRepository,
                GloyCash().container.pengeluaranRepository
            )
        }

        // Aset
        initializer {
            AsetHomeViewModel(
                GloyCash().container.asetRepository
            )
        }
        initializer {
            AsetInsertViewModel(
                GloyCash().container.asetRepository
            )
        }
        initializer {
            AsetDetailViewModel(
                createSavedStateHandle(),
                GloyCash().container.asetRepository
            )
        }
        initializer {
            AsetUpdateViewModel(
                createSavedStateHandle(),
                GloyCash().container.asetRepository
            )
        }

        // Kategori
        initializer {
            KategoriHomeViewModel(
                GloyCash().container.kategoriRepository
            )
        }
        initializer {
            KategoriInsertViewModel(
                GloyCash().container.kategoriRepository
            )
        }
        initializer {
            KategoriDetailViewModel(
                createSavedStateHandle(),
                GloyCash().container.kategoriRepository
            )
        }
        initializer {
            KategoriUpdateViewModel(
                createSavedStateHandle(),
                GloyCash().container.kategoriRepository
            )
        }

        // Pendapatan
        initializer {
            PendapatanHomeViewModel(
                GloyCash().container.pendapatanRepository,
                GloyCash().container.pengeluaranRepository
            )
        }
        initializer {
            PendapatanInsertViewModel(
                GloyCash().container.pendapatanRepository,
                GloyCash().container.kategoriRepository,
                GloyCash().container.asetRepository
            )
        }
        initializer {
            PendapatanDetailViewModel(
                createSavedStateHandle(),
                GloyCash().container.pendapatanRepository,
            )
        }
        initializer {
            PendapatanUpdateViewModel(
                createSavedStateHandle(),
                GloyCash().container.pendapatanRepository,
                GloyCash().container.kategoriRepository,
                GloyCash().container.asetRepository
            )
        }

        // Pengeluaran
        initializer {
            PengeluaranHomeViewModel(
                GloyCash().container.pengeluaranRepository,
                GloyCash().container.pendapatanRepository
            )
        }
        initializer {
            PengeluaranInsertViewModel(
                GloyCash().container.pengeluaranRepository,
                GloyCash().container.kategoriRepository,
                GloyCash().container.asetRepository
            )
        }
        initializer {
            PengeluaranDetailViewModel(
                createSavedStateHandle(),
                GloyCash().container.pengeluaranRepository,
            )
        }
        initializer {
            PengeluaranUpdateViewModel(
                createSavedStateHandle(),
                GloyCash().container.pengeluaranRepository,
            )
        }
    }
}

fun CreationExtras.GloyCash(): GloyCash =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GloyCash)