package com.example.registroocupaciones.domain.empleadoos.usecase

import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.empleados.usecase.ValidateEmpleadoUseCase
import javax.inject.Inject

class UpsertEmpleadoUseCase @Inject constructor(
    private val repository: EmpleadoRepository,
    private val validate: ValidateEmpleadoUseCase
){
    suspend operator fun invoke(empleado: Empleado): Result<Unit>{
        val validation = validate(
            nombres = empleado.nombres,
            sueldo = empleado.sueldo,
            fechaIngreso = empleado.fechaIngreso,
            sexo = empleado.sexo,
            currentId = empleado.empleadoId
        )
        return if(validation.isValid){
            repository.save(empleado)
            Result.success(Unit)
        }else{
            val error = validation.nombresError
                ?: validation.sueldoError
                ?: validation.fechaError
                ?: validation.sexoError
                ?: "Error de validación"
            Result.failure(Exception(error))
        }
    }
}
