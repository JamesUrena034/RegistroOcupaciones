package com.example.registroocupaciones.data.ocupaciones.mapper

import com.example.registroocupaciones.data.ocupaciones.local.entities.HoraExtraEntity
import com.example.registroocupaciones.domain.model.HoraExtra

fun HoraExtraEntity.toDomain() = HoraExtra(
    horaExtraId = horaExtraId,
    empleadoId = empleadoId,
    horasTrabajadasSemanales = horasTrabajadasSemanales,
    horasNormales = horasNormales,
    horasAl35 = horasAl35,
    horasAl100 = horasAl100,
    horasNocturnas = horasNocturnas,
    montoTotalHorasExtras = montoTotalHorasExtras,
    fechaRegistro = fechaRegistro
)

fun HoraExtra.toEntity() = HoraExtraEntity(
    horaExtraId = horaExtraId,
    empleadoId = empleadoId,
    horasTrabajadasSemanales = horasTrabajadasSemanales,
    horasNormales = horasNormales,
    horasAl35 = horasAl35,
    horasAl100 = horasAl100,
    horasNocturnas = horasNocturnas,
    montoTotalHorasExtras = montoTotalHorasExtras,
    fechaRegistro = fechaRegistro
)