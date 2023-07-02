package br.com.lambdateam.mycar.network

import br.com.lambdateam.mycar.model.maintenance.Maintenance
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ApiRepository(private val api: Api) {

    suspend fun getMaintenanceWithDetails(): Response<List<Maintenance>> {
        return try {
            api.getMaintenancesWithDetails()
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }
}