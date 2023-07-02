package br.com.lambdateam.mycar.network

import br.com.lambdateam.mycar.model.maintenance.Maintenance
import retrofit2.Response

class ApiRepository(private val api: Api) {

    suspend fun getMaintenanceWithDetails(): Response<List<Maintenance>> {
        return api.getMaintenancesWithDetails()
    }
}