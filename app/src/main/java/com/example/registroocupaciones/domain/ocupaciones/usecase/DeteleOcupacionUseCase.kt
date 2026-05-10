package com.example.registroocupaciones.domain.ocupaciones.usecase

import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion
import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import jakarta.inject.Inject

class DeleteOcupacionUseCase @Inject constructor(private val repo: OcupacionRepository) {
    suspend operator fun invoke(ocupacion: Ocupacion) = repo.delete(ocupacion)
}