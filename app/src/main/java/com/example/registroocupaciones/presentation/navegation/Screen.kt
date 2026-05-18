package com.example.registroocupaciones.presentation.navegation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object OcupacionList

    @Serializable
    data class Ocupacion(val ocupacionId: Int)

    @Serializable
    data object EmpleadoList

    @Serializable
    data class Empleado(val empleadoId: Int)
}