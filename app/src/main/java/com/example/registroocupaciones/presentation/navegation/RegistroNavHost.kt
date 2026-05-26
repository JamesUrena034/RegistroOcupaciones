package com.example.registroocupaciones.presentation.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.registroocupaciones.presentation.ocupacion.list.OcupacionListScreen
import com.example.registroocupaciones.presentation.ocupacion.edit.EditOcupacionScreen
import com.example.registroocupaciones.presentation.empleado.list.EmpleadoListScreen
import com.example.registroocupaciones.presentation.empleado.edit.EditEmpleadoScreen
import com.example.registroocupaciones.presentation.horaextra.edit.EditHoraExtraScreen
import com.example.registroocupaciones.presentation.horaextra.list.ListHoraExtraScreen
import com.example.registroocupaciones.presentation.navegation.Screen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()

    NavHost(
        navController = navHostController,
        startDestination = Screen.OcupacionList
    ) {
        composable<Screen.OcupacionList> {
            OcupacionListScreen(
                onDrawer = {
                    scope.launch { drawerState.open() }
                },
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
                onDrawer = {
                    scope.launch { drawerState.open() }
                }
            )
        }

        composable<Screen.EmpleadoList> {
            EmpleadoListScreen(
                onDrawer = {
                    scope.launch { drawerState.open() }
                },
                goToEmpleado = { id ->
                    navHostController.navigate(Screen.Empleado(id))
                },
                createEmpleado = {
                    navHostController.navigate(Screen.Empleado(0))
                }
            )
        }

        composable<Screen.Empleado> {
            val args = it.toRoute<Screen.Empleado>()
            EditEmpleadoScreen(
                empleadoId = args.empleadoId,
                onNavigateBack = { navHostController.navigateUp() },
                onDrawer = {
                    scope.launch { drawerState.open() }
                }
            )
        }

        composable<Screen.HoraExtraList> {
            ListHoraExtraScreen(
                onDrawer = {
                    scope.launch { drawerState.open() }
                },
                goToEditHoraExtra = { id ->
                    navHostController.navigate(Screen.HoraExtra(id))
                },
                createHoraExtra = {
                    navHostController.navigate(Screen.HoraExtra(0))
                }
            )
        }
        composable<Screen.HoraExtra> {
            val args = it.toRoute<Screen.HoraExtra>()
            EditHoraExtraScreen(
                horaExtraId = args.horaExtraId,
                onBack = { navHostController.navigateUp() }
            )
        }
    }
}