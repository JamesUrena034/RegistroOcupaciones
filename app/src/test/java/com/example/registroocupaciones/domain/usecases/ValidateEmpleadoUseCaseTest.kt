package com.example.registroocupaciones.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.empleados.usecase.ValidateEmpleadoUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ValidateEmpleadoUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: ValidateEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = ValidateEmpleadoUseCase(repository)
    }

    @Test
    fun `invoke guarda tarea con datos validos`() = runTest {
        // Given
        val nombres = "James Urena"
        val sueldo = 25000.0
        val fechaIngreso = "2026-05-17"
        val sexo = "M"
        coEvery { repository.getByNombres(nombres) } returns null

        // When
        val result = useCase(nombres, sueldo, fechaIngreso, sexo)

        // Then
        assertTrue(result.isValid)
        assertNull(result.nombresError)
        assertNull(result.sueldoError)
        assertNull(result.fechaError)
        assertNull(result.sexoError)
    }

    @Test
    fun `invoke falla con descripcion vacia`() = runTest {
        // Given
        val nombres = ""
        val sueldo = 25000.0
        val fechaIngreso = "2026-05-17"
        val sexo = "M"

        // When
        val result = useCase(nombres, sueldo, fechaIngreso, sexo)

        // Then
        assertFalse(result.isValid)
        assertEquals("El nombre es obligatorio", result.nombresError)
    }

    @Test
    fun `invoke falla con tiempo invalido`() = runTest {
        // Given
        val nombres = "James Urena"
        val sueldo = -5.0
        val fechaIngreso = "2026-05-17"
        val sexo = "M"
        coEvery { repository.getByNombres(nombres) } returns null

        // When
        val result = useCase(nombres, sueldo, fechaIngreso, sexo)

        // Then
        assertFalse(result.isValid)
        assertEquals("El sueldo debe ser mayor a 0", result.sueldoError)
    }
}