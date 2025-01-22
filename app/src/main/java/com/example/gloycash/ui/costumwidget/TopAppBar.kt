package com.example.gloycash.ui.costumwidget

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    showRefreshButton: Boolean = true
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Surface(
            color = colorResource(id = R.color.warna1),
            shadowElevation = 4.dp,
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Row(
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
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.me),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(shape = CircleShape)
                                    )
                                    Column {
                                        Text(
                                            text = "Welcome",
                                            fontSize = 13.sp,
                                            color = colorResource(id = R.color.white)
                                        )
                                        Text(
                                            text = "Defarrel Danendra Praja",
                                            fontSize = 15.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = colorResource(id = R.color.white)
                                        )
                                    }
                                }
                            }
                        }

                        if (showPageTitle) {
                            Text(
                                text = Judul,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(id = R.color.white),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.gloycash),
                        contentDescription = "Logo",
                        modifier = Modifier.size(100.dp)
                    )
                }

                if (showRefreshButton) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = onRefresh) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Refresh",
                                tint = colorResource(id = R.color.white)
                            )
                        }
                    }
                }

                if (showSaldo) {
                    Column(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Uang Anda Sekarang",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rp. $saldo",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = colorResource(id = R.color.white)
                        )
                    }
                }
            }
        }
    }
}







