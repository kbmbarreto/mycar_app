package br.com.lambdateam.mycar.domain

data class User(
    val id: Long,
    val user: String,
    val email: String,
    val mobileNumber: String,
    val password: String
)
