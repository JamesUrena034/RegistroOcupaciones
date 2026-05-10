package com.example.registroocupaciones.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.registroocupaciones.data.ocupaciones.local.OcupacionDao
import com.example.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity

class OcupacionDb {
    @Database(
        entities = [OcupacionEntity::class],
        version = 1,
        exportSchema = false
    )
    abstract class OcupacionDB : RoomDatabase() {
        abstract fun ocupacionesDao(): OcupacionDao
    }
}