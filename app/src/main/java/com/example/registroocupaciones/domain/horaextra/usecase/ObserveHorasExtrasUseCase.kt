package com.example.registroocupaciones.domain.horaextra.usecase

import com.example.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import jakarta.inject.Inject

class ObserveHorasExtrasUseCase @Inject constructor(
    private val repository: HoraExtraRepository
) {
    operator fun invoke() = repository.getAllHorasExtras()
}