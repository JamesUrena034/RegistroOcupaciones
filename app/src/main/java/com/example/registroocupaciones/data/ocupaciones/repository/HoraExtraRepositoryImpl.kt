package com.example.registroocupaciones.data.ocupaciones.repository

import com.example.registroocupaciones.data.ocupaciones.local.dao.HoraExtraDao
import com.example.registroocupaciones.data.ocupaciones.local.entities.HoraExtraEntity
import com.example.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.example.registroocupaciones.domain.model.HoraExtra
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HoraExtraRepositoryImpl @Inject constructor(
    private val horaExtraDao: HoraExtraDao
) : HoraExtraRepository {

    override suspend fun saveHoraExtra(horaExtra: HoraExtra) {
        val entity = horaExtra.toEntity()
        if (entity.horaExtraId == 0) {
            horaExtraDao.insertHoraExtra(entity)
        } else {
            horaExtraDao.updateHoraExtra(entity)
        }
    }

    override suspend fun deleteHoraExtra(horaExtra: HoraExtra) {
        horaExtraDao.deleteHoraExtra(horaExtra.toEntity())
    }

    override suspend fun getHoraExtraById(id: Int): HoraExtra? {
        return horaExtraDao.getHoraExtraById(id)?.toDomain()
    }

    override fun getHorasExtraByEmpleado(empleadoId: Int): Flow<List<HoraExtra>> {
        return horaExtraDao.getHorasExtrasByEmpleado(empleadoId).map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getAllHorasExtras(): Flow<List<HoraExtra>> {
        return horaExtraDao.getAllHorasExtras().map { list ->
            list.map { it.toDomain() }
        }
    }

    private fun HoraExtraEntity.toDomain() = HoraExtra(
        horaExtraId = horaExtraId,
        empleadoId = empleadoId,
        horasTrabajadasSemanales = horasTrabajadasSemanales,
        horasNormales = horasNormales,
        horasAl35 = horasAl35,
        horasAl100 = horasAl100,
        horasNocturnas = horasNocturnas,
        montoTotalHorasExtras = montoTotalHorasExtras,
        fechaRegistro = fechaRegistro
    )

    private fun HoraExtra.toEntity() = HoraExtraEntity(
        horaExtraId = horaExtraId,
        empleadoId = empleadoId,
        horasTrabajadasSemanales = horasTrabajadasSemanales,
        horasNormales = horasNormales,
        horasAl35 = horasAl35,
        horasAl100 = horasAl100,
        horasNocturnas = horasNocturnas,
        montoTotalHorasExtras = montoTotalHorasExtras,
        fechaRegistro = fechaRegistro
    )
}