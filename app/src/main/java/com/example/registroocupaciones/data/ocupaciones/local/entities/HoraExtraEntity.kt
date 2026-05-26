package com.example.registroocupaciones.data.ocupaciones.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.registroocupaciones.data.local.entities.EmpleadoEntity
@Entity(
    tableName = "horas_extras",
    foreignKeys = [
        ForeignKey(
            entity = EmpleadoEntity::class,
            parentColumns = ["empleadoId"],
            childColumns = ["empleadoId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["empleadoId"])]
)
data class HoraExtraEntity(
    @PrimaryKey(autoGenerate = true)
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