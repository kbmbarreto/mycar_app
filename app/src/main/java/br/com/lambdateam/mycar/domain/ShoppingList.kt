package br.com.lambdateam.mycar.domain

data class ShoppingList(
    val id: Long,
    val notes: String,
    val fullfiled: Boolean,
    val vehicle: Long,
    val component: Long
)
