package com.example.registroocupaciones.domain.ocupaciones.usecase

import com.example.registroocupaciones.domain.ocupaciones.model.Ocupacion
import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import javax.inject.Inject

class UpsertOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository,
    private val validate: ValidateOcupacionUseCase
) {
    suspend operator fun invoke(ocupacion: Ocupacion): Result<Unit> {
        val validation = validate(
            descripcion = ocupacion.descripcion,
            sueldo = ocupacion.sueldo,
            currentId = ocupacion.ocupacionId
        )

        return if (validation.isValid) {
            repository.save(ocupacion)
            Result.success(Unit)
        } else {
            val error = validation.descripcionError ?: validation.sueldoError ?: "Error"
            Result.failure(Exception(error))
        }
    }
}