package com.example.registroocupaciones.domain.model

data class HoraExtra(
    val horaExtraId: Int = 0,
    val empleadoId: Int,
    val horasTrabajadasSemanales: Double,
    val horasNormales: Double,
    val horasAl35: Double,
    val horasAl100: Double,
    val horasNocturnas: Double,
    val montoTotalHorasExtras: Double,
    val fechaRegistro: String
)