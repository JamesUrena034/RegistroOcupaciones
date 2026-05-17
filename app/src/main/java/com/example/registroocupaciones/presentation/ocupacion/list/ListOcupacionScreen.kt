package com.example.registroocupaciones.presentation.ocupacion.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion

@Composable
fun OcupacionListScreen(
    onDrawer: () -> Unit,
    goToOcupacion: (Int) -> Unit,
    createOcupacion: () -> Unit,
    viewModel: ListOcupacionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    OcupacionListBody(
        state = state,
        onDrawer = onDrawer,
        onEvent = { event ->
            when (event) {
                is ListOcupacionUiEvent.Edit -> goToOcupacion(event.id)
                is ListOcupacionUiEvent.CreateNew -> createOcupacion()
                else -> viewModel.onEvent(event)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OcupacionListBody(
    state: ListOcupacionUiState,
    onDrawer: () -> Unit,
    onEvent: (ListOcupacionUiEvent) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Ocupaciones") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ListOcupacionUiEvent.CreateNew) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                items(state.ocupaciones) { ocupacion ->
                    OcupacionCard(
                        ocupacion = ocupacion,
                        onClick = {
                            ocupacion.ocupacionId?.let { id ->
                                onEvent(ListOcupacionUiEvent.Edit(id))
                            }
                        },
                        onDelete = {
                            onEvent(ListOcupacionUiEvent.Delete(ocupacion))
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun OcupacionCard(
    ocupacion: Ocupacion,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ocupacion.descripcion,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Sueldo: $${ocupacion.sueldo}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OcupacionListPreview() {
    MaterialTheme {
        OcupacionListBody(
            state = ListOcupacionUiState(
                ocupaciones = listOf(
                    Ocupacion(1, "Ingeniero", 50000.0),
                    Ocupacion(2, "Maestro", 30000.0)
                )
            ),
            onDrawer = {},
            onEvent = {}
        )
    }
}