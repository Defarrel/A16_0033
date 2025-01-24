package com.example.gloycash.ui.costumwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gloycash.R

@Composable
fun TopAppBar(
    onBack: () -> Unit,
    showBackButton: Boolean = true,
    showProfile: Boolean = true,
    showSaldo: Boolean = true,
    showPageTitle: Boolean = true,
    Judul: String,
    saldo: String,
    saldoColor: Color = colorResource(id = R.color.white),
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    showRefreshButton: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth().padding(bottom = 16.dp)) {
        Surface(
            color = colorResource(id = R.color.warna1),
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(bottomEnd = 29.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (showBackButton) {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = colorResource(id = R.color.white)
                                )
                            }
                        }

                        if (showProfile) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Image(
                                painter = painterResource(id = R.drawable.me),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "Welcome",
                                    fontSize = 15.sp,
                                    color = colorResource(id = R.color.white)
                                )
                                Text(
                                    text = "Defarrel Danendra Praja",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorResource(id = R.color.white)
                                )
                            }
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.gloycash),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (showPageTitle) {
                        Text(
                            text = Judul,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    if (showSaldo) {
                        Column(
                            horizontalAlignment = Alignment.Start,
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            Text(
                                text = "Saldo Anda",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.white)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Rp. $saldo",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Medium,
                                color = saldoColor
                            )
                        }
                    }

                    if (showRefreshButton) {
                        IconButton(onClick = onRefresh) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Refresh",
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    }
                }
            }
        }
    }
}
