package com.example.registroocupaciones.presentation.horaextra.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHoraExtraScreen(
    onDrawer: () -> Unit,
    isTabletOrPC: Boolean,
    goToEditHoraExtra: (Int) -> Unit,
    createHoraExtra: () -> Unit,
    viewModel: ListHoraExtraViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Listado de Horas Extras") },
                navigationIcon = {
                    if (!isTabletOrPC) {
                        IconButton(onClick = onDrawer) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = createHoraExtra) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Horas Extras")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Total de registros: ${state.registros.size}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.DarkGray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (state.registros.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay registros de horas extras", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 340.dp),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(state.registros) { registro ->
                        val empleadoAsociado = state.empleados.find { it.empleadoId == registro.empleadoId }
                        val nombreEmpleado = empleadoAsociado?.nombres ?: "Empleado #${registro.empleadoId}"

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFEFEBF2)
                            ),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Text(
                                        text = "${registro.horaExtraId} - $nombreEmpleado",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 19.sp,
                                        color = Color.Black
                                    )

                                    Text(
                                        text = "Fecha: ${registro.fechaRegistro}",
                                        color = Color(0xFF757575),
                                        fontSize = 14.sp
                                    )

                                    Spacer(modifier = Modifier.height(2.dp))

                                    Text(
                                        text = "Horas totales: ${registro.horasTrabajadasSemanales} | Nocturnas: ${registro.horasNocturnas}",
                                        fontSize = 15.sp,
                                        color = Color(0xFF333333)
                                    )
                                    Text(
                                        text = "Al 35%: ${registro.horasAl35} hrs | Al 100%: ${registro.horasAl100} hrs",
                                        fontSize = 15.sp,
                                        color = Color(0xFF333333)
                                    )

                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 8.dp),
                                        color = Color(0xFFD7D3DB)
                                    )

                                    Text(
                                        text = "Total a Pagar: RD$ ${registro.montoTotalHorasExtras}",
                                        color = Color(0xFF3F51B5),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(20.dp),
                                    modifier = Modifier.padding(start = 12.dp)
                                ) {
                                    IconButton(
                                        onClick = { goToEditHoraExtra(registro.horaExtraId) },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Edit,
                                            contentDescription = "Editar",
                                            tint = Color.Black
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                        },
                                        modifier = Modifier.size(24.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Eliminar",
                                            tint = Color(0xFFEC1C24)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}