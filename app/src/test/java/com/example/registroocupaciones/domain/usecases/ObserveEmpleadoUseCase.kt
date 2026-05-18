package com.example.registroocupaciones.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.empleados.usecase.ObserveEmpleadoUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ObserveEmpleadoUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: ObserveEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = ObserveEmpleadoUseCase(repository)
    }

    @Test
    fun `observeTasks retorna flow de tareas`() = runTest {
        // Given
        val empleados = listOf(
            Empleado(
                empleadoId = 1,
                nombres = "Empleado 1",
                fechaIngreso = "2026-05-17",
                sexo = "M",
                sueldo = 30.0
            ),
            Empleado(
                empleadoId = 2,
                nombres = "Empleado 2",
                fechaIngreso = "2026-05-17",
                sexo = "F",
                sueldo = 45.0
            )
        )
        every { repository.observeAll() } returns flowOf(empleados)

        // When
        val result = useCase().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Empleado 1", result[0].nombres)
        assertEquals("Empleado 2", result[1].nombres)
        verify { repository.observeAll() }
    }
}