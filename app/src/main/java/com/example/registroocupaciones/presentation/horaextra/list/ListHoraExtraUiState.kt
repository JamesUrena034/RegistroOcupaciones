package com.example.registroocupaciones.presentation.horaextra.list

import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.model.HoraExtra

data class ListHoraExtraUiState(
    val isLoading: Boolean = false,
    val registros: List<HoraExtra> = emptyList(),
    val empleados: List<Empleado> = emptyList(),
    val error: String? = null
)