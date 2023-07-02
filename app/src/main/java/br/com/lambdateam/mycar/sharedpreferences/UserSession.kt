package br.com.lambdateam.mycar.sharedpreferences

interface UserSession {
    fun setToken(token: String?)
    fun getToken(): String?
}