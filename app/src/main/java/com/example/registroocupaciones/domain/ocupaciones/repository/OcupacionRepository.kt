package com.example.registroocupaciones.domain.ocupaciones.repository

import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion
import kotlinx.coroutines.flow.Flow

interface OcupacionRepository {
    fun observeAll(): Flow<List<Ocupacion>>
    suspend fun getById(id: Int): Ocupacion?
    suspend fun getByDescripcion(descripcion: String): Ocupacion?
    suspend fun save(ocupacion: Ocupacion)
    suspend fun delete(ocupacion: Ocupacion)
}