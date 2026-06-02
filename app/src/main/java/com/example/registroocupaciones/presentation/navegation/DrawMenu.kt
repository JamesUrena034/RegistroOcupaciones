package com.example.registroocupaciones.presentation.navegation

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun DrawMenu(
    drawerState: DrawerState,
    navHostController: NavHostController,
    content: @Composable () -> Unit
) {
    val selectedItem = remember { mutableStateOf("Ocupaciones") }
    val scope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val isTabletOrPC = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    fun handleItemClick(destination: Any, item: String) {
        navHostController.navigate(destination) {
            popUpTo(navHostController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
        selectedItem.value = item
        scope.launch { drawerState.close() }
    }

    val menuItemsContent = @Composable {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Registro Recursos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            item {
                DrawerItem(
                    title = "Ocupaciones",
                    icon = Icons.Filled.Work,
                    isSelected = selectedItem.value == "Ocupaciones"
                ) {
                    handleItemClick(Screen.OcupacionList, "Ocupaciones")
                }

                DrawerItem(
                    title = "Empleados",
                    icon = Icons.Filled.People,
                    isSelected = selectedItem.value == "Empleados"
                ) {
                    handleItemClick(Screen.EmpleadoList, "Empleados")
                }

                DrawerItem(
                    title = "Horas Extras",
                    icon = Icons.Filled.AccessTime,
                    isSelected = selectedItem.value == "Horas Extras"
                ) {
                    handleItemClick(Screen.HoraExtraList, "Horas Extras")
                }
            }
        }
    }

    if (isTabletOrPC) {
        PermanentNavigationDrawer(
            drawerContent = {
                PermanentDrawerSheet(
                    modifier = Modifier
                        .width(280.dp)
                        .fillMaxHeight()
                ) {
                    menuItemsContent()
                }
            }
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    } else {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    modifier = Modifier.width(280.dp)
                ) {
                    menuItemsContent()
                }
            }
        ) {
            content()
        }
    }
}