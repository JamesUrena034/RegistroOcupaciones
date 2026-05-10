package com.example.registroocupaciones.data.ocupaciones.mapper

import com.example.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity
import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion

fun OcupacionEntity.toDomain(): Ocupacion =
    Ocupacion(
        ocupacionId = ocupacionId,
        descripcion = descripcion,
        sueldo = sueldo
    )

fun Ocupacion.toEntity(): OcupacionEntity =
    OcupacionEntity(
        ocupacionId = ocupacionId,
        descripcion = descripcion,
        sueldo = sueldo
    )