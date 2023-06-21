package br.com.lambdateam.mycar.domain

import java.util.Date

data class Maintenances(
    val id: Long,
    val km: Double,
    val maintenanteDate: Date,
    val nextKm: Double,
    val amount: Double,
    val manufacturer: Long,
    val vehicle: Long,
    val component: Long,
    val maintenanceType: Long
)