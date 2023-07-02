package br.com.lambdateam.mycar.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import org.koin.android.BuildConfig

class UserSessionImpl(context: Context) : UserSession {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        "${context.packageName}.${BuildConfig.BUILD_TYPE}",
        Context.MODE_PRIVATE
    )

    private companion object {
        const val TOKEN_KEY = "TOKEN_KEY"
    }

    override fun setToken(token: String) {
        sharedPreferences.edit().putString(TOKEN_KEY, token).apply()
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }
}