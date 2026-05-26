package com.example.registroocupaciones.domain.horaextra.usecase

import com.example.registroocupaciones.domain.empleadoos.model.Empleado
import com.example.registroocupaciones.domain.model.HoraExtra
import jakarta.inject.Inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalcularHorasExtrasUseCase @Inject constructor() {

    operator fun invoke(empleado: Empleado, horasTotales: Double, horasNocturnas: Double, idExistente: Int = 0, fechaExistente: String? = null): HoraExtra {
        val sueldoPorDia = empleado.sueldo / 23.53
        val valorHoraNormal = sueldoPorDia / 8

        val valorHora35 = valorHoraNormal * 1.35
        val valorHora100 = valorHoraNormal * 2.0
        val plusNocturno = valorHoraNormal * 0.15

        val horasNormales = if (horasTotales > 44.0) 44.0 else horasTotales
        val horasExtrasTotales = if (horasTotales > 44.0) horasTotales - 44.0 else 0.0

        val horasAl35 = if (horasExtrasTotales > 24.0) 24.0 else horasExtrasTotales
        val horasAl100 = if (horasExtrasTotales > 24.0) horasExtrasTotales - 24.0 else 0.0

        val monto35 = horasAl35 * valorHora35
        val monto100 = horasAl100 * valorHora100
        val montoNocturno = horasNocturnas * plusNocturno

        val montoTotal = monto35 + monto100 + montoNocturno
        val fecha = fechaExistente ?: SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
            Date()
        )

        return HoraExtra(
            horaExtraId = idExistente,
            empleadoId = empleado.empleadoId ?: 0,
            horasTrabajadasSemanales = horasTotales,
            horasNormales = horasNormales,
            horasAl35 = horasAl35,
            horasAl100 = horasAl100,
            horasNocturnas = horasNocturnas,
            montoTotalHorasExtras = Math.round(montoTotal * 100.0) / 100.0,
            fechaRegistro = fecha
        )
    }
}