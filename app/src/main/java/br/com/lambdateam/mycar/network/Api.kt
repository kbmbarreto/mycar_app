package br.com.lambdateam.mycar.network

import br.com.lambdateam.mycar.model.login.LoginDTO
import br.com.lambdateam.mycar.model.login.LoginResponse
import br.com.lambdateam.mycar.model.maintenance.Maintenance
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @GET("maintenances/withDetails")
    suspend fun getMaintenancesWithDetails(
        @Header("Authorization") auth: String
    ): Response<List<Maintenance>>

    @POST("authenticate")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body login: LoginDTO
    ): Response<LoginResponse>
}