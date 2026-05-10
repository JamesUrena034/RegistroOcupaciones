package com.example.registroocupaciones.domain.ocupaciones.usecase

import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import jakarta.inject.Inject

class GetOcupacionUseCase @Inject constructor(private val repo: OcupacionRepository) {
    suspend operator fun invoke(id: Int) = repo.getById(id)
}