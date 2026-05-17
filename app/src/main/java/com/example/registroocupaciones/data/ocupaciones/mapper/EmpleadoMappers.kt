package com.example.registroocupaciones.data.ocupaciones.mapper

import com.example.registroocupaciones.data.local.entities.EmpleadoEntity
import com.example.registroocupaciones.domain.empleadoos.model.Empleado

fun EmpleadoEntity.toDomain(): Empleado =
    Empleado(
        empleadoId = empleadoId,
        fechaIngreso = fechaIngreso,
        nombres = nombres,
        sexo = sexo,
        sueldo = sueldo
    )
fun Empleado.toEntity(): EmpleadoEntity =
    EmpleadoEntity(
        empleadoId = empleadoId,
        fechaIngreso = fechaIngreso,
        nombres = nombres,
        sexo = sexo,
        sueldo = sueldo
    )