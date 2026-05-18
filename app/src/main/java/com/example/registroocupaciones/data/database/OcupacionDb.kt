package com.example.registroocupaciones.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registroocupaciones.data.local.dao.EmpleadoDao
import com.example.registroocupaciones.data.local.entities.EmpleadoEntity
import com.example.registroocupaciones.data.ocupaciones.local.dao.OcupacionDao
import com.example.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity

class OcupacionDb {
    @Database(
        entities = [
            OcupacionEntity::class,
            EmpleadoEntity::class
        ],
        version = 2,
        exportSchema = false
    )
    abstract class OcupacionDB : RoomDatabase() {
        abstract fun ocupacionesDao(): OcupacionDao
        abstract fun empleadoDao(): EmpleadoDao
    }
}