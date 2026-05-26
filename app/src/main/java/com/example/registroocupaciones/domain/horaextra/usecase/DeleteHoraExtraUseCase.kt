package com.example.registroocupaciones.domain.horaextra.usecase

import com.example.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.example.registroocupaciones.domain.model.HoraExtra
import jakarta.inject.Inject

class DeleteHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
) {
    suspend operator fun invoke(horaExtra: HoraExtra) = repository.deleteHoraExtra(horaExtra)
}