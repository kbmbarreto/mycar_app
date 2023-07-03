package br.com.lambdateam.mycar.views.maintenance

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel

class MaintenanceDetailActivity : AppCompatActivity() {

    private val ivClose by lazy { findViewById<ImageView>(R.id.iv_maintenance_detail_close) }
    private val tvDate by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_date) }
    private val tvVehicle by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_vehicle) }
    private val tvKm by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_km) }
    private val tvType by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_type) }
    private val tvComponent by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_component) }
    private val tvAmount by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_amount) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_detail)

        getParcelable()
        setListeners()
    }

    private fun setListeners() {
        ivClose.setOnClickListener {
            finish()
        }
    }

    private fun getParcelable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MAINTENANCE_ITEM, MaintenancePresentModel::class.java)?.let {
                fillScreenValues(it)
            }
        } else {
            intent.getParcelableExtra<MaintenancePresentModel>(MAINTENANCE_ITEM)?.let {
                fillScreenValues(it)
            }
        }
    }

    private fun fillScreenValues(it: MaintenancePresentModel) {
        tvDate.text = it.maintenanceDate
        tvVehicle.text = it.vehicle
        tvKm.text = it.km
        tvType.text = it.maintenanceType
        tvComponent.text = it.component
        tvAmount.text = it.amount
    }

    companion object {
        private const val MAINTENANCE_ITEM = "MAINTENANCE_ITEM"
        fun start(context: Context, maintenanceItem: MaintenancePresentModel) {
            val intent = Intent(context, MaintenanceDetailActivity::class.java)
            intent.putExtra(MAINTENANCE_ITEM, maintenanceItem)
            startActivity(context, intent, null)
        }
    }
}