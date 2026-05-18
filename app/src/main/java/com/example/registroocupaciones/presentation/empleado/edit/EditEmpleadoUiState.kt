package com.example.registroocupaciones.presentation.empleado.edit

data class EditEmpleadoUiState(
    val isNew: Boolean = true,
    val isSaving: Boolean = false,
    val isDeleting: Boolean = false,
    val saved: Boolean = false,
    val deleted: Boolean = false,
    val empleadoId: Int? = null,
    val nombres: String = "",
    val nombresError: String? = null,
    val fechaIngreso: String = "",
    val fechaIngresoError: String? = null,
    val sexo: String = "",
    val sexoError: String? = null,
    val sueldo: String = "",
    val sueldoError: String? = null
)