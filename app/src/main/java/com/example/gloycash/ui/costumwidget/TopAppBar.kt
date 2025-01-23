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
import androidx.compose.foundation.shape.RoundedCornerShape
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
    saldoColor: Color = colorResource(id = R.color.white),
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    showRefreshButton: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Surface(
            color = colorResource(id = R.color.warna1),
            shadowElevation = 8.dp,
            shape = RoundedCornerShape(bottomEnd = 29.dp)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.align(Alignment.CenterStart),
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
                                        .clip(CircleShape)
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

                    Image(
                        painter = painterResource(id = R.drawable.gloycash),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                }

                if (showPageTitle) {
                    Text(
                        text = Judul,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.white),
                        modifier = Modifier.padding(start = 16.dp, top = 3.dp, bottom = 16.dp)
                    )
                }

                if (showSaldo) {
                    Column(
                        modifier = Modifier
                            .padding(start = 30.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Saldo Anda Sekarang",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.white)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Rp. $saldo",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = saldoColor
                        )
                    }
                }
            }
        }

        if (showRefreshButton) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                IconButton(onClick = onRefresh) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = "Refresh",
                        tint = colorResource(id = R.color.warna1)
                    )
                }
            }
        }
    }
}

