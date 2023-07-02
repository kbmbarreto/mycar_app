package br.com.lambdateam.mycar.di

import android.app.Application
import br.com.lambdateam.mycar.network.Api
import br.com.lambdateam.mycar.network.ApiRepository
import br.com.lambdateam.mycar.views.maintenance.MaintenanceViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
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
            properties(mapOf(PROPERTY_BASE_URL to "http://localhost:8003/mycar/v1/"))
        }
    }
}

val appModule = module {
    factory { ApiRepository(get()) }
    single { MaintenanceViewModel(get()) }
    single<Api>{
        val retrofit = get() as Retrofit
        retrofit.create(Api::class.java)
    }
}