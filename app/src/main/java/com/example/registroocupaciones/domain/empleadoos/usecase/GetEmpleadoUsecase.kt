package com.example.registroocupaciones.domain.empleados.usecase

import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import javax.inject.Inject

class GetEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    suspend operator fun invoke(id: Int) = repository.getById(id)
}