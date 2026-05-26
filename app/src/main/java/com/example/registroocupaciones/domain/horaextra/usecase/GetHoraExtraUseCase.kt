package com.example.registroocupaciones.domain.horaextra.usecase

import com.example.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import jakarta.inject.Inject

class GetHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository
) {
    suspend operator fun invoke(id: Int) = repository.getHoraExtraById(id)
}