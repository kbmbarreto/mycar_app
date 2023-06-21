package br.com.lambdateam.mycar.domain

import java.util.Date

data class TrafficTicket(
    val id: Long,
    val description: String,
    val date: Date,
    val notes: String,
    val vehicle: Long
    )
