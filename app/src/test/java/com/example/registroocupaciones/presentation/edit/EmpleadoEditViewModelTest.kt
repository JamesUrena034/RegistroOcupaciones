package com.example.registroocupaciones.presentation.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.empleadoos.usecase.UpsertEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.DeleteEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.GetEmpleadoUseCase
import com.example.registroocupaciones.domain.empleados.usecase.ValidateEmpleadoUseCase
import com.example.registroocupaciones.presentation.empleado.edit.EditEmpleadoUiEvent
import com.example.registroocupaciones.presentation.empleado.edit.EditEmpleadoViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class EditEmpleadoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: EditEmpleadoViewModel
    private lateinit var getEmpleadoUseCase: GetEmpleadoUseCase
    private lateinit var upsertEmpleadoUseCase: UpsertEmpleadoUseCase
    private lateinit var deleteEmpleadoUseCase: DeleteEmpleadoUseCase
    private lateinit var validateEmpleadoUseCase: ValidateEmpleadoUseCase

    @Before
    fun setup() {
        getEmpleadoUseCase = mockk()
        upsertEmpleadoUseCase = mockk()
        deleteEmpleadoUseCase = mockk()
        validateEmpleadoUseCase = mockk()

        viewModel = EditEmpleadoViewModel(
            getEmpleadoUseCase,
            upsertEmpleadoUseCase,
            deleteEmpleadoUseCase,
            validateEmpleadoUseCase
        )
    }

    @Test
    fun `onEvent Load carga los datos del empleado correctamente`() = runTest {
        // Given
        val id = 1
        val empleado = Empleado(
            empleadoId = id,
            nombres = "James Urena",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 35000.0
        )
        coEvery { getEmpleadoUseCase(id) } returns empleado

        // When
        viewModel.onEvent(EditEmpleadoUiEvent.Load(id))
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.isNew)
        assertEquals(id, viewModel.state.value.empleadoId)
        assertEquals("James Urena", viewModel.state.value.nombres)
        assertEquals("35000.0", viewModel.state.value.sueldo)
        coVerify { getEmpleadoUseCase(id) }
    }

    @Test
    fun `onEvent Save guarda con datos validos exitosamente`() = runTest {
        // Given
        viewModel.onEvent(EditEmpleadoUiEvent.NombresChanged("Enmanuel"))
        viewModel.onEvent(EditEmpleadoUiEvent.FechaIngresoChanged("2026-05-17"))
        viewModel.onEvent(EditEmpleadoUiEvent.SexoChanged("M"))
        viewModel.onEvent(EditEmpleadoUiEvent.SueldoChanged("40000.0"))

        coEvery {
            validateEmpleadoUseCase(
                nombres = "Enmanuel",
                sueldo = 40000.0,
                fechaIngreso = "2026-05-17",
                sexo = "M",
                currentId = null
            )
        } returns ValidateEmpleadoUseCase.ValidationResult(isValid = true)

        coEvery { upsertEmpleadoUseCase(any()) } returns Result.success(Unit)

        // When
        viewModel.onEvent(EditEmpleadoUiEvent.Save)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.saved)
        assertFalse(viewModel.state.value.isSaving)
        coVerify { upsertEmpleadoUseCase(any()) }
    }

    @Test
    fun `onEvent Save falla cuando la validacion es invalida`() = runTest {
        // Given
        viewModel.onEvent(EditEmpleadoUiEvent.NombresChanged(""))
        viewModel.onEvent(EditEmpleadoUiEvent.SueldoChanged("200.0"))

        coEvery {
            validateEmpleadoUseCase(
                nombres = "",
                sueldo = 200.0,
                fechaIngreso = "",
                sexo = "",
                currentId = null
            )
        } returns ValidateEmpleadoUseCase.ValidationResult(
            isValid = false,
            nombresError = "El nombre es obligatorio"
        )

        // When
        viewModel.onEvent(EditEmpleadoUiEvent.Save)
        advanceUntilIdle()

        // Then
        assertFalse(viewModel.state.value.saved)
        assertEquals("El nombre es obligatorio", viewModel.state.value.nombresError)
        coVerify(exactly = 0) { upsertEmpleadoUseCase(any()) }
    }

    @Test
    fun `onEvent Delete elimina el empleado correctamente`() = runTest {
        // Given
        val id = 1
        val empleado = Empleado(
            empleadoId = id,
            nombres = "James",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 1000.0
        )
        coEvery { getEmpleadoUseCase(id) } returns empleado
        coEvery { deleteEmpleadoUseCase(any()) } returns Unit

        viewModel.onEvent(EditEmpleadoUiEvent.Load(id))
        advanceUntilIdle()

        // When
        viewModel.onEvent(EditEmpleadoUiEvent.Delete)
        advanceUntilIdle()

        // Then
        assertTrue(viewModel.state.value.deleted)
        coVerify { deleteEmpleadoUseCase(any()) }
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}