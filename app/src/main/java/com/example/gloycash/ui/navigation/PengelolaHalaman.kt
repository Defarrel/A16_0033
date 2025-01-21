package com.example.gloycash.ui.navigation

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

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeKategori.route,
        modifier = Modifier,
        ) {
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
        }
    }*/
        composable(route = DestinasiHomeKategori.route) {
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
        }
    }
}