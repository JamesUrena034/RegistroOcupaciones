package com.example.registroocupaciones.domain.ocupaciones.model

data class Ocupacion(
    val ocupacionId: Int? = null,
    val descripcion: String = "",
    val sueldo: Double = 0.0
)