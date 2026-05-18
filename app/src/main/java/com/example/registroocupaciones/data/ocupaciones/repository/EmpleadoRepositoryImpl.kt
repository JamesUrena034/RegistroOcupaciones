package com.example.registroocupaciones.data.repository

import com.example.registroocupaciones.data.local.dao.EmpleadoDao
import com.example.registroocupaciones.data.ocupaciones.mapper.toDomain
import com.example.registroocupaciones.data.ocupaciones.mapper.toEntity
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmpleadoRepositoryImpl @Inject constructor(
    private val empleadoDao: EmpleadoDao
) : EmpleadoRepository {

    override fun observeAll(): Flow<List<Empleado>> {
        return empleadoDao.getAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getById(id: Int): Empleado? {
        return empleadoDao.getById(id)?.toDomain()
    }

    override suspend fun getByNombres(nombres: String): Empleado? {
        return empleadoDao.getByNombres(nombres)?.toDomain()
    }

    override suspend fun save(empleado: Empleado) {
        empleadoDao.save(empleado.toEntity())
    }

    override suspend fun delete(empleado: Empleado) {
        empleadoDao.delete(empleado.toEntity())
    }
}