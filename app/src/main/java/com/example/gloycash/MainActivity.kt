package com.example.gloycash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gloycash.ui.theme.GloyCashTheme
import com.example.gloycash.ui.viewmodel.GloyCashApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GloyCashTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    GloyCashApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
