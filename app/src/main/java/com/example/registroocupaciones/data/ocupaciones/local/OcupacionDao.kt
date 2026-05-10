package com.example.registroocupaciones.data.ocupaciones.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.registroocupaciones.data.ocupaciones.local.entities.OcupacionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OcupacionDao {

    @Query("SELECT * FROM ocupaciones ORDER BY ocupacionId DESC")
    fun getAll(): Flow<List<OcupacionEntity>>

    @Query("SELECT * FROM ocupaciones WHERE ocupacionId = :id")
    suspend fun getById(id: Int): OcupacionEntity?


    @Query("SELECT * FROM ocupaciones WHERE descripcion = :descripcion LIMIT 1")
    suspend fun getByDescripcion(descripcion: String): OcupacionEntity?

    @Upsert
    suspend fun save(ocupacion: OcupacionEntity)

    @Delete
    suspend fun delete(ocupacion: OcupacionEntity)
}