package com.example.gloycash

import android.app.Application
import com.example.gloycash.di.AppContainer
import com.example.gloycash.di.KeuanganKontainer

class GloyCash: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = KeuanganKontainer()
    }
}