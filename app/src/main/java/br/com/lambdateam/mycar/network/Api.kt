package br.com.lambdateam.mycar.network

import br.com.lambdateam.mycar.model.maintenance.Maintenance
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface Api {

    @GET("maintenances/withDetails")
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJrbGViZXIuYmFycmV0b0Btc24uY29tIiwiaWF0IjoxNjg4MjQ0NjA0LCJleHAiOjE2ODgyODA2MDR9.AbQaZZFi7UN6sjyZYPB3NgMKkKC9_0N3tAGSjOqS7h8")
    suspend fun getMaintenancesWithDetails(): Response<List<Maintenance>>

}