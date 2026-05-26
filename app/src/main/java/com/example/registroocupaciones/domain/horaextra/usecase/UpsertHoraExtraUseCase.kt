package com.example.registroocupaciones.domain.horaextra.usecase

import com.example.registroocupaciones.domain.horaextra.repository.HoraExtraRepository
import com.example.registroocupaciones.domain.model.HoraExtra
import jakarta.inject.Inject

class UpsertHoraExtraUseCase @Inject constructor(
    private val repository: HoraExtraRepository,
    private val validate: ValidateHoraExtraUseCase
) {
    suspend operator fun invoke(horaExtra: HoraExtra): Result<Unit> {
        val validation = validate(
            empleadoId = horaExtra.empleadoId,
            horasSemanales = horaExtra.horasTrabajadasSemanales,
            horasNocturnas = horaExtra.horasNocturnas
        )
        return if (validation.isValid) {
            repository.saveHoraExtra(horaExtra)
            Result.success(Unit)
        } else {
            val error = validation.empleadoError
                ?: validation.horasSemanalesError
                ?: validation.horasNocturnasError
                ?: "Error de validación"
            Result.failure(Exception(error))
        }
    }
}