package com.example.registroocupaciones.domain.empleadoos.repository

import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import kotlinx.coroutines.flow.Flow

interface EmpleadoRepository {
    fun observeAll(): Flow<List<Empleado>>
    suspend fun getById(id: Int): Empleado?
    suspend fun getByNombres(nombres: String): Empleado?
    suspend fun save(empleado: Empleado)
    suspend fun delete(empleado: Empleado)
}