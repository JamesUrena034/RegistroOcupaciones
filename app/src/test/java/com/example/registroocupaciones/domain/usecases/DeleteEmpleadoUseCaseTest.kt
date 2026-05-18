package com.example.registroocupaciones.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.repository.EmpleadoRepository
import com.example.registroocupaciones.domain.empleados.usecase.DeleteEmpleadoUseCase
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteEmpleadoUseCaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var useCase: DeleteEmpleadoUseCase
    private lateinit var repository: EmpleadoRepository

    @Before
    fun setup() {
        repository = mockk()
        useCase = DeleteEmpleadoUseCase(repository)
    }

    @Test
    fun `delete elimina tarea correctamente`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 1,
            nombres = "James",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 2500.0
        )
        coEvery { repository.delete(empleado) } returns Unit

        // When
        useCase(empleado)

        // Then
        coVerify { repository.delete(empleado) }
    }
}