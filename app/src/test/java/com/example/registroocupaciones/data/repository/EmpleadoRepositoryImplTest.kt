package com.example.registroocupaciones.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registroocupaciones.data.local.dao.EmpleadoDao
import com.example.registroocupaciones.data.local.entities.EmpleadoEntity
import com.example.registroocupaciones.domain.empleadoos.model.Empleado
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
class EmpleadoRepositoryImplTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var repository: EmpleadoRepositoryImpl
    private lateinit var dao: EmpleadoDao

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = EmpleadoRepositoryImpl(dao)
    }

    @Test
    fun `upsert guarda tarea correctamente`() = runTest {
        // Given
        val empleado = Empleado(
            empleadoId = 0,
            nombres = "Nuevo Empleado",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 30.0
        )
        coEvery { dao.save(any()) } returns Unit

        // When
        repository.save(empleado)

        // Then
        coVerify { dao.save(any()) }
    }

    @Test
    fun `upsert actualiza tarea correctamente`() = runTest {
        // Given
        val empleado = Empleado(empleadoId = 1, nombres = "Empleado actualizado", fechaIngreso = "2026-05-17", sexo = "M", sueldo = 45.0)
        coEvery { dao.save(any()) } returns Unit

        // When
        repository.save(empleado)

        // Then
        coVerify { dao.save(any()) }
    }

    @Test
    fun `delete elimina tarea correctamente`() = runTest {
        // Given
        val empleado = Empleado(empleadoId = 1, nombres = "James", fechaIngreso = "2026-05-17", sexo = "M", sueldo = 2500.0)
        coEvery { dao.delete(any()) } just Runs

        // When
        repository.delete(empleado)

        // Then
        coVerify { dao.delete(any()) }
    }

    @Test
    fun `observeTasks retorna flow de tareas`() = runTest {
        // Given
        val listaEntities = listOf(
            EmpleadoEntity(
                empleadoId = 1,
                nombres = "Empleado 1",
                fechaIngreso = "2026-05-17",
                sexo = "M",
                sueldo = 30.0
            ),
            EmpleadoEntity(
                empleadoId = 2,
                nombres = "Empleado 2",
                fechaIngreso = "2026-05-17",
                sexo = "F",
                sueldo = 45.0
            )
        )
        every { dao.getAll() } returns flowOf(listaEntities)

        // When
        val result = repository.observeAll().first()

        // Then
        assertEquals(2, result.size)
        assertEquals("Empleado 1", result[0].nombres)
        assertEquals("Empleado 2", result[1].nombres)
    }

    @Test
    fun `getTask retorna tarea por id`() = runTest {
        // Given
        val entity = EmpleadoEntity(
            empleadoId = 1,
            nombres = "Empleadd Test",
            fechaIngreso = "2026-05-17",
            sexo = "M",
            sueldo = 30.0
        )
        coEvery { dao.getById(1) } returns entity

        // When
        val result = repository.getById(1)

        // Then
        assertNotNull(result)
        assertEquals("Empleado Test", result?.nombres)
        assertEquals(30.0, result?.sueldo ?: 0.0, 0.0)
    }
}