package com.example.registroocupaciones.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.registroocupaciones.presentation.list.OcupacionListScreen
import com.example.registroocupaciones.presentation.edit.EditOcupacionScreen
import com.example.registroocupaciones.presentation.navegation.Screen

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.OcupacionList
    ) {
        composable<Screen.OcupacionList> {
            OcupacionListScreen(
                onDrawer = { },
                goToOcupacion = { id ->
                    navHostController.navigate(Screen.Ocupacion(id))
                },
                createOcupacion = {
                    navHostController.navigate(Screen.Ocupacion(0))
                }
            )
        }


        composable<Screen.Ocupacion> {
            val args = it.toRoute<Screen.Ocupacion>()
            EditOcupacionScreen(
                ocupacionId = args.ocupacionId,
                onNavigateBack = { navHostController.navigateUp() },
                onDrawer = {}
            )
        }
    }
}