package com.example.registroocupaciones.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.registroocupaciones.data.local.entities.EmpleadoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EmpleadoDao {

    @Query("SELECT * FROM Empleados ORDER BY empleadoId DESC")
    fun getAll(): Flow<List<EmpleadoEntity>>

    @Query("SELECT * FROM Empleados WHERE empleadoId = :id")
    suspend fun getById(id: Int): EmpleadoEntity?

    @Query("SELECT * FROM Empleados WHERE nombres = :nombres LIMIT 1")
    suspend fun getByNombres(nombres: String): EmpleadoEntity?

    @Upsert
    suspend fun save(empleado: EmpleadoEntity)

    @Delete
    suspend fun delete(empleado: EmpleadoEntity)
}