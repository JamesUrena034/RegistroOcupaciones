package com.example.registroocupaciones.domain.ocupaciones.usecase

import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import jakarta.inject.Inject

class ObserveOcupacionUseCase @Inject constructor(private val repo: OcupacionRepository) {
    operator fun invoke() = repo.observeAll()
}