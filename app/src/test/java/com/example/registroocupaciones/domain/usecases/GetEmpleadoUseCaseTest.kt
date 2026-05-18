package com.example.registroocupaciones.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.empleados.usecase.GetEmpleadoUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetEmpleadoUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: GetEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetEmpleadoUseCase(repository)
    }

    @Test
    fun `invoke retorna el empleado cuando existe el id`() = runTest {
        // Given
        val id = 1
        val empleadoEsperado = Empleado(
            empleadoId = id,
            nombres = "James Urena",
            fechaIngreso = "2026-05-18",
            sexo = "M",
            sueldo = 45000.0
        )
        coEvery { repository.getById(id) } returns empleadoEsperado

        // When
        val result = useCase(id)

        // Then
        assertNotNull(result)
        assertEquals(id, result?.empleadoId)
        assertEquals("James Urena", result?.nombres)
        coVerify { repository.getById(id) }
    }

    @Test
    fun `invoke retorna null cuando el empleado no existe`() = runTest {
        // Given
        val id = 99
        coEvery { repository.getById(id) } returns null

        // When
        val result = useCase(id)

        // Then
        assertNull(result)
        coVerify { repository.getById(id) }
    }
}