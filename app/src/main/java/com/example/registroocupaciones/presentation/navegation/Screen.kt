package com.example.registroocupaciones.presentation.navegation

import kotlinx.serialization.Serializable
sealed class Screen {
    @Serializable
    data object OcupacionList

    @Serializable
    data class Ocupacion(val ocupacionId: Int)
}