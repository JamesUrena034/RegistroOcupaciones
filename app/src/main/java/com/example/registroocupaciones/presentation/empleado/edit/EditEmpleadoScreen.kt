package com.example.registroocupaciones.presentation.empleado.edit

import android.app.DatePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditEmpleadoScreen(
    empleadoId: Int?,
    onNavigateBack: () -> Unit,
    onDrawer: () -> Unit,
    viewModel: EditEmpleadoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(empleadoId) {
        viewModel.onEvent(EditEmpleadoUiEvent.Load(empleadoId))
    }

    if (state.saved || state.deleted) {
        SideEffect {
            onNavigateBack()
        }
    }

    EditEmpleadoBody(
        state = state,
        onEvent = { event -> viewModel.onEvent(event) },
        onNavigateBack = onNavigateBack,
        onDrawer = onDrawer
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditEmpleadoBody(
    state: EditEmpleadoUiState,
    onEvent: (EditEmpleadoUiEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onDrawer: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val monthFormatted = String.format("%02d", month + 1)
            val dayFormatted = String.format("%02d", dayOfMonth)
            onEvent(EditEmpleadoUiEvent.FechaIngresoChanged("$year-$monthFormatted-$dayFormatted"))
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(if (state.isNew) "Nuevo Empleado" else "Editar Empleado") },
                navigationIcon = {
                    IconButton(onClick = onDrawer) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu")
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                    OutlinedTextField(
                        value = state.nombres,
                        onValueChange = { onEvent(EditEmpleadoUiEvent.NombresChanged(it)) },
                        label = { Text("Nombres") },
                        placeholder = { Text("Ej: Juan Pérez") },
                        isError = state.nombresError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.nombresError?.let { Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(12.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = state.fechaIngreso,
                            onValueChange = {},
                            label = { Text("Fecha") },
                            placeholder = { Text("Seleccione la fecha") },
                            isError = state.fechaIngresoError != null,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false,
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = if (state.fechaIngresoError != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.outline,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { datePickerDialog.show() }
                        )
                    }
                    state.fechaIngresoError?.let { Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.sexo,
                        onValueChange = { onEvent(EditEmpleadoUiEvent.SexoChanged(it)) },
                        label = { Text("Sexo") },
                        placeholder = { Text("M o F") },
                        isError = state.sexoError != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.sexoError?.let { Text(text = it, color = Color.Red, style = MaterialTheme.typography.bodySmall) }

                    Spacer(Modifier.height(12.dp))

                    OutlinedTextField(
                        value = state.sueldo,
                        onValueChange = { onEvent(EditEmpleadoUiEvent.SueldoChanged(it)) },
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
                            onClick = { onEvent(EditEmpleadoUiEvent.Save) },
                            modifier = Modifier.weight(1f),
                            enabled = !state.isSaving
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(4.dp))
                            Text("Guardar")
                        }

                        if (!state.isNew) {
                            OutlinedButton(
                                onClick = { onEvent(EditEmpleadoUiEvent.Delete) },
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

@Preview(showBackground = true)
@Composable
fun EditEmpleadoPreview() {
    MaterialTheme {
        EditEmpleadoBody(
            state = EditEmpleadoUiState(
                nombres = "Carlos Mendoza",
                fechaIngreso = "2026-01-15",
                sexo = "M",
                sueldo = "35000.0",
                isNew = false
            ),
            onEvent = {},
            onNavigateBack = {},
            onDrawer = {}
        )
    }
}