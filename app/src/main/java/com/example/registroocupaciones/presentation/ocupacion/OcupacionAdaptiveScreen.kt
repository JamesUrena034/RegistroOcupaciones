package com.example.registroocupaciones.presentation.ocupacion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.toRoute
import com.example.registroocupaciones.presentation.navegation.Screen
import com.example.registroocupaciones.presentation.ocupacion.edit.EditOcupacionScreen
import com.example.registroocupaciones.presentation.ocupacion.list.OcupacionListScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun OcupacionAdaptiveScreen(
    navHostController: NavHostController,
    onDrawer: () -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<Int>()
    val scope = rememberCoroutineScope()

    val currentRoute = navHostController.currentBackStackEntry?.toRoute<Screen.Ocupacion>()
    val argumentId = currentRoute?.ocupacionId

    LaunchedEffect(argumentId) {
        if (argumentId != null) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, argumentId)
        } else if (navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded && navigator.currentDestination == null) {
            navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, 0)
        }
    }

    val isTabletOrPC = navigator.scaffoldValue[ListDetailPaneScaffoldRole.List] == PaneAdaptedValue.Expanded &&
            navigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail] == PaneAdaptedValue.Expanded

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            OcupacionListScreen(
                onDrawer = onDrawer,
                isTabletOrPC = isTabletOrPC,
                goToOcupacion = { id ->
                    scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, id) }
                },
                createOcupacion = {
                    scope.launch { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, 0) }
                }
            )
        },
        detailPane = {
            val selectedId = navigator.currentDestination?.contentKey ?: argumentId
            if (selectedId != null) {
                EditOcupacionScreen(
                    ocupacionId = selectedId,
                    onNavigateBack = {
                        scope.launch { navigator.navigateBack() }
                    },
                    onDrawer = onDrawer,
                    isTabletOrPC = isTabletOrPC
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}