package com.example.registroocupaciones.domain.ocupaciones.usecase

import com.example.registroocupaciones.domain.ocupaciones.repository.OcupacionRepository
import javax.inject.Inject

class ValidateOcupacionUseCase @Inject constructor(
    private val repository: OcupacionRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val descripcionError: String? = null,
        val sueldoError: String? = null
    )

    suspend operator fun invoke(
        descripcion: String,
        sueldo: Double?,
        currentId: Int? = null
    ): ValidationResult {

        val descripcionError = when {
            descripcion.isBlank() -> "La descripción es obligatoria"
            else -> {
                val existing = repository.getByDescripcion(descripcion)
                if (existing != null && existing.ocupacionId != currentId) {
                    "Esta ocupación ya está registrada"
                } else null
            }
        }

        val sueldoError = when {
            sueldo == null || sueldo <= 0 -> "El sueldo debe ser mayor a 0"
            else -> null
        }

        return ValidationResult(
            isValid = descripcionError == null && sueldoError == null,
            descripcionError = descripcionError,
            sueldoError = sueldoError
        )
    }
}