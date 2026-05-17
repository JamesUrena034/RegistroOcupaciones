package com.example.registroocupaciones.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Empleados")
data class EmpleadoEntity(
    @PrimaryKey(autoGenerate = true)
    val empleadoId: Int? = null,
    val fechaIngreso: String = "",
    val nombres: String = "",
    val sexo: String = "",
    val sueldo: Double = 0.0
)