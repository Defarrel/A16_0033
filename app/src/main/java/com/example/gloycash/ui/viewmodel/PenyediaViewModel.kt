package com.example.gloycash.ui.viewmodel

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

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            PendapatanHomeViewModel(
                GloyCash().container.pendapatanRepository)
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
    }
}

fun CreationExtras.GloyCash(): GloyCash =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GloyCash)