package com.example.registroocupaciones.presentation.navegation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object OcupacionList : Screen()

    @Serializable
    data class Ocupacion(val ocupacionId: Int = 0) : Screen()

    @Serializable
    data object EmpleadoList : Screen()

    @Serializable
    data class Empleado(val empleadoId: Int = 0) : Screen()

    @Serializable
    data object HoraExtraList : Screen()

    @Serializable
    data class HoraExtra(val horaExtraId: Int = 0) : Screen()
}