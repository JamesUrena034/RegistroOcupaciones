package com.example.registroocupaciones.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.registroocupaciones.presentation.ocupacion.OcupacionAdaptiveScreen
import com.example.registroocupaciones.presentation.empleado.EmpleadoAdaptiveScreen
import com.example.registroocupaciones.presentation.horaextra.HoraExtraAdaptiveScreen
import com.example.registroocupaciones.presentation.navegation.Screen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController,
    drawerState: DrawerState,
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navHostController,
        startDestination = Screen.OcupacionList
    ) {
        composable<Screen.OcupacionList> {
            OcupacionAdaptiveScreen(
                navHostController = navHostController,
                onDrawer = { scope.launch { drawerState.open() } }
            )
        }

        composable<Screen.Ocupacion> {
            OcupacionAdaptiveScreen(
                navHostController = navHostController,
                onDrawer = { scope.launch { drawerState.open() } }
            )
        }

        composable<Screen.EmpleadoList> {
            EmpleadoAdaptiveScreen(
                navHostController = navHostController,
                onDrawer = { scope.launch { drawerState.open() } }
            )
        }

        composable<Screen.Empleado> {
            EmpleadoAdaptiveScreen(
                navHostController = navHostController,
                onDrawer = { scope.launch { drawerState.open() } }
            )
        }

        composable<Screen.HoraExtraList> {
            HoraExtraAdaptiveScreen(
                navHostController = navHostController,
                onDrawer = { scope.launch { drawerState.open() } }
            )
        }

        composable<Screen.HoraExtra> {
            HoraExtraAdaptiveScreen(
                navHostController = navHostController,
                onDrawer = { scope.launch { drawerState.open() } }
            )
        }
    }
}