package com.example.registroocupaciones.presentation.ocupacion.edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditOcupacionScreen(
    ocupacionId: Int?,
    onNavigateBack: () -> Unit,
    onDrawer: () -> Unit,
    isTabletOrPC: Boolean,
    viewModel: EditOcupacionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(ocupacionId) {
        viewModel.onEvent(EditOcupacionUiEvent.Load(ocupacionId))
    }

    if (state.saved || state.deleted) {
        SideEffect {
            onNavigateBack()
        }
    }

    EditOcupacionBody(
        state = state,
        onEvent = viewModel::onEvent,
        onNavigateBack = onNavigateBack,
        onDrawer = onDrawer,
        isTabletOrPC = isTabletOrPC
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditOcupacionBody(
    state: EditOcupacionUiState,
    onEvent: (EditOcupacionUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onDrawer: () -> Unit,
    isTabletOrPC: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (state.isNew) "Nueva Ocupación" else "Editar Ocupación") },
                navigationIcon = {
                    if (!isTabletOrPC) {
                        IconButton(onClick = onDrawer) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                        OutlinedTextField(
                            value = state.descripcion,
                            onValueChange = { onEvent(EditOcupacionUiEvent.DescripcionChanged(it)) },
                            label = { Text("Descripción") },
                            placeholder = { Text("Ej: Ingeniero de Software") },
                            isError = state.descripcionError != null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        state.descripcionError?.let { Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                        Spacer(Modifier.height(12.dp))

                        OutlinedTextField(
                            value = state.sueldo,
                            onValueChange = { onEvent(EditOcupacionUiEvent.SueldoChanged(it)) },
                            label = { Text("Sueldo") },
                            prefix = { Text("$ ") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            isError = state.sueldoError != null,
                            modifier = Modifier.fillMaxWidth()
                        )
                        state.sueldoError?.let { Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                        Spacer(Modifier.height(24.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { onEvent(EditOcupacionUiEvent.Save) },
                                modifier = Modifier.weight(1f),
                                enabled = !state.isSaving
                            ) {
                                Icon(Icons.Default.Add, contentDescription = null)
                                Spacer(Modifier.width(4.dp))
                                Text("Guardar")
                            }

                            if (!state.isNew) {
                                OutlinedButton(
                                    onClick = { onEvent(EditOcupacionUiEvent.Delete) },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                                    enabled = !state.isDeleting
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = null)
                                    Spacer(Modifier.width(4.dp))
                                    Text("Eliminar")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditOcupacionPreview() {
    MaterialTheme {
        EditOcupacionBody(
            state = EditOcupacionUiState(
                descripcion = "Analista de Datos",
                sueldo = "45000.0",
                isNew = false
            ),
            onEvent = {},
            onNavigateBack = {},
            onDrawer = {},
            isTabletOrPC = false
        )
    }
}