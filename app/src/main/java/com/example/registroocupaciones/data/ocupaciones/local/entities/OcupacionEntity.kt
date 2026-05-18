package com.example.registroocupaciones.data.ocupaciones.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ocupaciones")
data class OcupacionEntity(
    @PrimaryKey(autoGenerate = true)
    val ocupacionId: Int? = null,
    val descripcion: String = "",
    val sueldo: Double = 0.0
)