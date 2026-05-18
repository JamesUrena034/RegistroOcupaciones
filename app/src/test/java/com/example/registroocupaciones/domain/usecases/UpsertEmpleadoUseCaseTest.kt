package com.example.registroocupaciones.domain.usecases

import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.empleadoos.usecase.UpsertEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.ValidateEmpleadoUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UpsertEmpleadoUseCaseTest {

    private lateinit var useCase: UpsertEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository
    private lateinit var validator: ValidateEmpleadoUseCase

    @Before
    fun setup() {
        repository = mockk()
        validator = mockk()
        useCase = UpsertEmpleadoUseCase(repository, validator)
    }

    @Test
    fun `invoke guarda tarea con datos validos`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            nombres = "Test Empleado",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 30.0
        )

        coEvery {
            validator(
                nombres = empleado.nombres,
                sueldo = empleado.sueldo,
                fechaIngreso = empleado.fechaIngreso,
                sexo = empleado.sexo,
                currentId = empleado.empleadoId
            )
        } returns ValidateEmpleadoUseCase.ValidationResult(isValid = true)

        coEvery { repository.save(empleado) } returns Unit

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isSuccess)
        coVerify { repository.save(empleado) }
    }

    @Test
    fun `invoke falla con descripcion vacia`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            nombres = "",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 30.0
        )

        coEvery {
            validator(
                nombres = empleado.nombres,
                sueldo = empleado.sueldo,
                fechaIngreso = empleado.fechaIngreso,
                sexo = empleado.sexo,
                currentId = empleado.empleadoId
            )
        } returns ValidateEmpleadoUseCase.ValidationResult(
            isValid = false,
            nombresError = "El nombre es obligatorio"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        coVerify(exactly = 0) { repository.save(any()) }
    }

    @Test
    fun `invoke falla con tiempo invalido`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            nombres = "Test Empleado",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = -5.0
        )

        coEvery {
            validator(
                nombres = empleado.nombres,
                sueldo = empleado.sueldo,
                fechaIngreso = empleado.fechaIngreso,
                sexo = empleado.sexo,
                currentId = empleado.empleadoId
            )
        } returns ValidateEmpleadoUseCase.ValidationResult(
            isValid = false,
            sueldoError = "El sueldo debe ser mayor a 0"
        )

        // When
        val result = useCase(empleado)

        // Then
        assertTrue(result.isFailure)
        coVerify(exactly = 0) { repository.save(any()) }
    }
}