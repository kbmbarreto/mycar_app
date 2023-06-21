package br.com.lambdateam.mycar.domain

import java.util.Date

data class Service(
    val id: Long,
    val scheduling: Date,
    val description: String,
    val orderService: String,
    val vehicle: Long,
    val workshop: Long
)
