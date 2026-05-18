package com.example.registroocupaciones.domain.empleados.usecase

import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import javax.inject.Inject

class ValidateEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    data class ValidationResult(
        val isValid: Boolean,
        val nombresError: String? = null,
        val sueldoError: String? = null,
        val fechaError: String? = null,
        val sexoError: String? = null
    )

    suspend operator fun invoke(
        nombres: String,
        sueldo: Double?,
        fechaIngreso: String,
        sexo: String,
        currentId: Int? = null
    ): ValidationResult {

        val nombresError = when {
            nombres.isBlank() -> "El nombre es obligatorio"
            else -> {
                val existing = repository.getByNombres(nombres)
                if (existing != null && existing.empleadoId != currentId) {
                    "Este empleado ya está registrado"
                } else null
            }
        }

        val sueldoError = when {
            sueldo == null || sueldo <= 0 -> "El sueldo debe ser mayor a 0"
            else -> null
        }

        val fechaError = if (fechaIngreso.isBlank()) "La fecha es obligatoria" else null

        val sexoError = if (sexo.isBlank()) "Debe seleccionar el sexo" else null

        return ValidationResult(
            isValid = nombresError == null && sueldoError == null && fechaError == null && sexoError == null,
            nombresError = nombresError,
            sueldoError = sueldoError,
            fechaError = fechaError,
            sexoError = sexoError
        )
    }
}