package br.com.lambdateam.mycar.network

import br.com.lambdateam.mycar.model.login.LoginDTO
import br.com.lambdateam.mycar.model.login.LoginResponse
import br.com.lambdateam.mycar.model.maintenance.Maintenance
import br.com.lambdateam.mycar.model.maintenance.MaintenanceComponent
import br.com.lambdateam.mycar.model.maintenance.MaintenanceDTO
import br.com.lambdateam.mycar.model.maintenance.MaintenanceManufacturer
import br.com.lambdateam.mycar.model.maintenance.MaintenanceType
import br.com.lambdateam.mycar.model.maintenance.MaintenanceVehicle
import br.com.lambdateam.mycar.sharedpreferences.UserSession
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class ApiRepository(private val api: Api, private val userSession: UserSession) {

    suspend fun getMaintenanceWithDetails(): Response<List<Maintenance>> {
        return try {
            api.getMaintenancesWithDetails("Bearer ${userSession.getToken()}")
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }

    suspend fun login(loginDTO: LoginDTO): Response<LoginResponse> {
        return try {
            api.login(loginDTO)
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }

    suspend fun getComponents(): Response<List<MaintenanceComponent>> {
        return try {
            api.getComponents("Bearer ${userSession.getToken()}")
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }

    suspend fun getVehicles(): Response<List<MaintenanceVehicle>> {
        return try {
            api.getVehicles("Bearer ${userSession.getToken()}")
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }

    suspend fun getManufacturers(): Response<List<MaintenanceManufacturer>> {
        return try {
            api.getManufacturers("Bearer ${userSession.getToken()}")
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }

    suspend fun getMaintenanceTypes(): Response<List<MaintenanceType>> {
        return try {
            api.getMaintenanceTypes("Bearer ${userSession.getToken()}")
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }

    suspend fun createMaintenance(maintenanceDTO: MaintenanceDTO): Response<Unit> {
        return try {
            api.createMaintenance("Bearer ${userSession.getToken()}", maintenanceDTO)
        } catch (_: Exception) {
            Response.error(400, "".toResponseBody())
        }
    }
}