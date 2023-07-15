package br.com.lambdateam.mycar.network

import br.com.lambdateam.mycar.model.login.LoginDTO
import br.com.lambdateam.mycar.model.login.LoginResponse
import br.com.lambdateam.mycar.model.maintenance.Maintenance
import br.com.lambdateam.mycar.model.maintenance.MaintenanceComponent
import br.com.lambdateam.mycar.model.maintenance.MaintenanceDTO
import br.com.lambdateam.mycar.model.maintenance.MaintenanceManufacturer
import br.com.lambdateam.mycar.model.maintenance.MaintenanceType
import br.com.lambdateam.mycar.model.maintenance.MaintenanceVehicle
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @POST("authenticate")
    @Headers("Content-Type: application/json")
    suspend fun login(
        @Body login: LoginDTO
    ): Response<LoginResponse>

    @GET("maintenances/withDetails")
    suspend fun getMaintenancesWithDetails(
        @Header("Authorization") auth: String
    ): Response<List<Maintenance>>

    @GET("component")
    suspend fun getComponents(
        @Header("Authorization") auth: String
    ): Response<List<MaintenanceComponent>>

    @GET("vehicle")
    suspend fun getVehicles(
        @Header("Authorization") auth: String
    ): Response<List<MaintenanceVehicle>>

    @GET("manufacturer")
    suspend fun getManufacturers(
        @Header("Authorization") auth: String
    ): Response<List<MaintenanceManufacturer>>

    @GET("maintenanceType")
    suspend fun getMaintenanceTypes(
        @Header("Authorization") auth: String
    ): Response<List<MaintenanceType>>

    @POST("maintenances")
    @Headers("Content-Type: application/json")
    suspend fun createMaintenance(
        @Header("Authorization") auth: String,
        @Body maintenance: MaintenanceDTO
    ): Response<Unit>
}