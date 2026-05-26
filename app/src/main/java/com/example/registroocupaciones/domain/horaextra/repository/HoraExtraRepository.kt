package com.example.registroocupaciones.domain.horaextra.repository

import com.example.registroocupaciones.domain.model.HoraExtra
import kotlinx.coroutines.flow.Flow

interface HoraExtraRepository {
    suspend fun saveHoraExtra(horaExtra: HoraExtra)
    suspend fun deleteHoraExtra(horaExtra: HoraExtra)
    suspend fun getHoraExtraById(id: Int): HoraExtra?
    fun getHorasExtraByEmpleado(empleadoId: Int): Flow<List<HoraExtra>>
    fun getAllHorasExtras(): Flow<List<HoraExtra>>
}