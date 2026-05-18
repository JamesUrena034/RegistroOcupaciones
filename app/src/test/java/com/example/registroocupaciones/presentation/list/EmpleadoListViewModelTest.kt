package com.example.registroocupaciones.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleados.usecase.ObserveEmpleadoUseCase
import com.example.registroocupaciones.presentation.empleado.list.EmpleadoListViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class EmpleadoListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: EmpleadoListViewModel
    private lateinit var observeEmpleadoUseCase: ObserveEmpleadoUseCase

    @Before
    fun setup() {
        observeEmpleadoUseCase = mockk()

        every { observeEmpleadoUseCase() } returns flowOf(emptyList())

        viewModel = EmpleadoListViewModel(
            observeEmpleadoUseCase
        )
    }

    @Test
    fun `loadTasks carga lista de tareas correctamente`() = runTest {
        // Given
        val empleados = listOf(
            Empleado(empleadoId = 1, nombres = "Empleado 1", fechaIngreso = "2026-05-17", sexo = "M", sueldo = 30.0),
            Empleado(empleadoId = 2, nombres = "Empleado 2", fechaIngreso = "2026-05-17", sexo = "F", sueldo = 45.0)
        )
        every { observeEmpleadoUseCase() } returns flowOf(empleados)

        // When
        viewModel = EmpleadoListViewModel(observeEmpleadoUseCase)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isLoading)
        assertEquals(2, viewModel.state.value.empleados.size)
        assertEquals("Empleado 1", viewModel.state.value.empleados[0].nombres)
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}