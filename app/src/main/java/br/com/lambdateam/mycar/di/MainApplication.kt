package br.com.lambdateam.mycar.di

import android.app.Application
import br.com.lambdateam.mycar.network.Api
import br.com.lambdateam.mycar.network.ApiRepository
import br.com.lambdateam.mycar.sharedpreferences.UserSession
import br.com.lambdateam.mycar.sharedpreferences.UserSessionImpl
import br.com.lambdateam.mycar.views.login.LoginViewModel
import br.com.lambdateam.mycar.views.main.MainViewModel
import br.com.lambdateam.mycar.views.maintenance.AddMaintenanceViewModel
import br.com.lambdateam.mycar.views.maintenance.MaintenanceDetailViewModel
import br.com.lambdateam.mycar.views.maintenance.MaintenanceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule, retrofit)
            properties(mapOf(PROPERTY_BASE_URL to "http://kleber2732.c34.integrator.host/mycar/v1/"))
        }
    }
}

val appModule = module {
    factory { ApiRepository(get(), get()) }
    single { MaintenanceViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { AddMaintenanceViewModel(get()) }
    viewModel { MaintenanceDetailViewModel(get()) }
    single<Api> {
        val retrofit = get() as Retrofit
        retrofit.create(Api::class.java)
    }
    single<UserSession> { UserSessionImpl(androidContext()) }
}