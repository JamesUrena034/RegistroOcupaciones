package com.example.registroocupaciones.domain.empleados.usecase

import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import javax.inject.Inject

class ObserveEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository
) {
    operator fun invoke() = repository.observeAll()
}