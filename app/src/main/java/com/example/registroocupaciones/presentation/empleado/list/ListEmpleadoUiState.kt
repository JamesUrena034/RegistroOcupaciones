package com.example.registroocupaciones.presentation.empleado.list

import com.example.registroocupaciones.domain.empleadoos.model.Empleado

data class EmpleadoListUiState(
    val empleados: List<Empleado> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)