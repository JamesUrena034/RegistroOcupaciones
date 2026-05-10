package com.example.registroocupaciones.data.ocupaciones.repository

import com.example.registroocupaciones.data.ocupaciones.local.OcupacionDao
import com.example.registroocupaciones.data.ocupaciones.mapper.toDomain
import com.example.registroocupaciones.data.ocupaciones.mapper.toEntity
import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion
import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OcupacionRepositoryImpl @Inject constructor(
    private val ocupacionesDao: OcupacionDao
) : OcupacionRepository {

    override fun observeAll(): Flow<List<Ocupacion>> {
        return ocupacionesDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Int): Ocupacion? {
        return ocupacionesDao.getById(id)?.toDomain()
    }

    override suspend fun getByDescripcion(descripcion: String): Ocupacion? {
        return ocupacionesDao.getByDescripcion(descripcion)?.toDomain()
    }

    override suspend fun save(ocupacion: Ocupacion) {
        ocupacionesDao.save(ocupacion.toEntity())
    }

    override suspend fun delete(ocupacion: Ocupacion) {
        ocupacionesDao.delete(ocupacion.toEntity())
    }
}