package br.com.lambdateam.mycar.di

import org.koin.core.qualifier.StringQualifier
import org.koin.core.scope.Scope
import retrofit2.Retrofit

inline fun <reified T> Scope.resolveRetrofit(qualifier: StringQualifier? = null): T? {
    val retrofit = qualifier?.let { get(it) as Retrofit } ?: get() as Retrofit
    return retrofit.create(T::class.java)
}