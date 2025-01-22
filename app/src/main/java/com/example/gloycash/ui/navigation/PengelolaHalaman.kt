package com.example.gloycash.ui.navigation

import PendapatanDetailView
import PendapatanInsertView
import PengeluaranDetailView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gloycash.ui.view.aset.AsetDetailView
import com.example.gloycash.ui.view.aset.AsetHomeView
import com.example.gloycash.ui.view.aset.AsetInsertView
import com.example.gloycash.ui.view.aset.AsetUpdateView
import com.example.gloycash.ui.view.kategori.KategoriDetailView
import com.example.gloycash.ui.view.kategori.KategoriHomeView
import com.example.gloycash.ui.view.kategori.KategoriInsertView
import com.example.gloycash.ui.view.kategori.KategoriUpdateView
import com.example.gloycash.ui.view.pendapatan.PendapatanHomeView
import com.example.gloycash.ui.view.pendapatan.PendapatanUpdateView
import com.example.gloycash.ui.view.pengeluaran.PengeluaranHomeView
import com.example.gloycash.ui.view.pengeluaran.PengeluaranInsertView
import com.example.gloycash.ui.view.pengeluaran.PengeluaranUpdateView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiInsertPengeluaran.route,
        modifier = Modifier,
    ) {
        composable(route = DestinasiInsertPengeluaran.route) {
            PengeluaranInsertView(
                navigateBack = {
                    navController.navigate(DestinasiHomePengeluaran.route) {
                        popUpTo(DestinasiHomePengeluaran.route) { inclusive = true }
                    }
                },
                navigateToPengeluaran = {
                    navController.navigate(DestinasiHomePengeluaran.route) {
                        popUpTo(DestinasiHomePengeluaran.route) { inclusive = true }
                    }
                }
            )
        }
        composable(route = DestinasiHomePengeluaran.route) {
            PengeluaranHomeView(
                navigateToInsert = {
                    navController.navigate(DestinasiInsertPengeluaran.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPengeluaran.route}/$id") {
                    }
                    println("Detail Pengeluaran ID: $id")
                }
            )
        }
        composable(route = DestinasiDetailPengeluaran.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPengeluaran.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiDetailPengeluaran.ID)
            id?.let {
                PengeluaranDetailView(
                    navigateBack = {
                        navController.navigate(DestinasiHomePengeluaran.route) {
                            popUpTo(DestinasiHomePengeluaran.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdatePengeluaran.route}/$id")
                    },
                    onNavigateToHome = {
                        navController.navigate(DestinasiHomePengeluaran.route) {
                            popUpTo(DestinasiHomePengeluaran.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(route = DestinasiUpdatePengeluaran.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePengeluaran.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiUpdatePengeluaran.ID)
            id?.let {
                PengeluaranUpdateView(
                    navigateBack = {
                        navController.navigate("${DestinasiHomePengeluaran.route}") {
                            popUpTo(DestinasiHomePengeluaran.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }



        //Pendapatan
/*
        composable(route = DestinasiInsertPendapatan.route) {
            PendapatanInsertView(
                navigateBack = {
                    navController.navigate(DestinasiHomePendapatan.route) {
                        popUpTo(DestinasiHomePendapatan.route) { inclusive = true }
                    }
                },
                navigateToPendapatan = {
                    navController.navigate(DestinasiHomePendapatan.route) {
                        popUpTo(DestinasiHomePendapatan.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = DestinasiHomePendapatan.route) {
            PendapatanHomeView(
                navigateToInsert = {
                    navController.navigate(DestinasiInsertPendapatan.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailPendapatan.route}/$id") {
                    }
                    println("Detail Pendapatan ID: $id")
                }
            )
        }

        composable(
            DestinasiDetailPendapatan.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailPendapatan.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiDetailPendapatan.ID)
            id?.let {
                PendapatanDetailView(
                    navigateBack = {
                        navController.navigate(DestinasiHomePendapatan.route) {
                            popUpTo(DestinasiHomePendapatan.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdatePendapatan.route}/$id")
                    },
                    onNavigateToHome = {
                        navController.navigate(DestinasiHomePendapatan.route) {
                            popUpTo(DestinasiHomePendapatan.route) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdatePendapatan.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdatePendapatan.ID){
                    type = NavType.StringType
                }
            )
        ){
            val nim = it.arguments?.getString(DestinasiUpdatePendapatan.ID)
            nim?.let {
                PendapatanUpdateView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailPendapatan.route}/$it") {
                            popUpTo("${DestinasiDetailPendapatan.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }


*/


        //aset
    /*        composable(route = DestinasiHomeAset.route)
            {
                AsetHomeView(
                    navigateToInsert = {
                        navController.navigate(DestinasiInsertAset.route)
                    },
                    onDetailClick = {id ->
                        navController.navigate("${DestinasiDetailAset.route}/$id")
                        println("Detail Aset ID: $id")
                    }
                )
            }
            composable(route = DestinasiInsertAset.route){
                AsetInsertView(
                    navigateBack = {
                        navController.navigate(DestinasiHomeAset.route)
                    }
                )
            }
            composable(
                DestinasiDetailAset.routeWithArg,
                arguments = listOf(
                    navArgument(DestinasiDetailAset.ID){
                        type = NavType.StringType
                    }
                )
            ) {
                val nim = it.arguments?.getString(DestinasiDetailAset.ID)
                nim?.let {
                    AsetDetailView(
                        navigateBack = {
                            navController.navigate("${DestinasiHomeAset.route}") {
                                popUpTo("${DestinasiHomeAset.route}") {
                                    inclusive = true
                                }
                            }
                        },
                        navigateToEdit =  {
                            navController.navigate("${DestinasiUpdateAset.route}/$it")
                        },
                        onNavigateToHome = {
                            navController.navigate("${DestinasiHomeAset.route}") {
                                popUpTo("${DestinasiHomeAset.route}"){
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
            composable(
                DestinasiUpdateAset.routeWithArg,
                arguments = listOf(
                    navArgument(DestinasiUpdateAset.ID){
                        type = NavType.StringType
                    }
                )
            ){
                val nim = it.arguments?.getString(DestinasiUpdateAset.ID)
                nim?.let {
                    AsetUpdateView(
                        navigateBack = {
                            navController.navigate("${DestinasiHomeAset.route}") {
                                popUpTo("${DestinasiHomeAset.route}") {
                                    inclusive = true
                                }
                            }
                        },
                    )
                }
            }*/

        //Kategori
/*        composable(route = DestinasiHomeKategori.route) {
            KategoriHomeView(
                navigateToInsert = {
                    navController.navigate(DestinasiInsertKategori.route)
                },
                onDetailClick = { id ->
                    navController.navigate("${DestinasiDetailKategori.route}/$id")
                    println("Detail Kategori ID: $id")
                }
            )
        }
        composable(route = DestinasiInsertKategori.route) {
            KategoriInsertView(
                navigateBack = {
                    navController.navigate(DestinasiHomeKategori.route)
                }
            )
        }
        composable(
            DestinasiDetailKategori.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKategori.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiDetailKategori.ID)
            id?.let {
                KategoriDetailView(
                    navigateBack = {
                        navController.navigate("${DestinasiHomeKategori.route}") {
                            popUpTo("${DestinasiHomeKategori.route}") {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdateKategori.route}/$it")
                    },
                    onNavigateToHome = {
                        navController.navigate("${DestinasiHomeKategori.route}") {
                            popUpTo("${DestinasiHomeKategori.route}") {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
        composable(
            DestinasiUpdateKategori.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKategori.ID) {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString(DestinasiUpdateKategori.ID)
            id?.let {
                KategoriUpdateView(
                    navigateBack = {
                        navController.navigate("${DestinasiHomeKategori.route}") {
                            popUpTo("${DestinasiHomeKategori.route}") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }*/
    }
}