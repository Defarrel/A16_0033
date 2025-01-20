package com.example.gloycash.ui.costumwidget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gloycash.R

@Composable
fun BottomAppBar(
    showTambahClick: Boolean = true,
    showFormAddClick: Boolean = true,
    onPendapatanClick: () -> Unit = {},
    onPengeluaranClick: () -> Unit = {},
    onAsetClick: () -> Unit = {},
    onKategoriClick: () -> Unit = {},
    onAdd: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = modifier,
            color = colorResource(id = R.color.warna1),
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconWithText(
                    icon = Icons.Default.CheckCircle,
                    text = "Pendapatan",
                    onClick = onPendapatanClick
                )
                IconWithText(
                    icon = Icons.Default.ShoppingCart,
                    text = "Pengeluaran",
                    onClick = onPengeluaranClick
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconWithText(
                    icon = Icons.Default.Info,
                    text = "Aset",
                    onClick = onAsetClick
                )
                IconWithText(
                    icon = Icons.Default.ThumbUp,
                    text = "Kategori",
                    onClick = onKategoriClick
                )
            }
        }

        if (showTambahClick) {
            FloatingActionButton(
                modifier = modifier
                    .offset(y = (-28).dp)
                    .size(75.dp),
                onClick = onAdd,
                shape = CircleShape,
                containerColor = colorResource(id = R.color.warna3),
                contentColor = colorResource(id = R.color.white)
            ) {
                Icon(
                    modifier = modifier.size(30.dp),
                    imageVector = if (showFormAddClick) Icons.Default.Add else Icons.Default.Home,
                    contentDescription = if (showFormAddClick) "Add" else "Home"
                )
            }
        }
    }
}

@Composable
fun IconWithText(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = colorResource(id = R.color.white)
            )
        }
        Text(
            text = text,
            fontSize = 12.sp,
            color = colorResource(id = R.color.white),
            fontWeight = FontWeight.Medium
        )
    }
}

