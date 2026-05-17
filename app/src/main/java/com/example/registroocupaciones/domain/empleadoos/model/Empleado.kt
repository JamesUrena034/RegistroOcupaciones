package com.example.registroocupaciones.domain.empleadoos.model

data class Empleado (
    val empleadoId: Int? = null,
    val fechaIngreso: String = "",
    val nombres: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0
)