package com.example.registroocupaciones.presentation.empleado.list

import com.example.registroocupaciones.domain.empleadoos.model.Empleado

sealed interface ListEmpleadoUiEvent {
    data object Load : ListEmpleadoUiEvent
    data class Delete(val empleado: Empleado) : ListEmpleadoUiEvent
    data object CreateNew : ListEmpleadoUiEvent
    data class Edit(val id: Int) : ListEmpleadoUiEvent
    data class ShowMessage(val message: String) : ListEmpleadoUiEvent
}