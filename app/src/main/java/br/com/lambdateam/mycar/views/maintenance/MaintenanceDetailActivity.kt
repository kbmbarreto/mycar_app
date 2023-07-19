package br.com.lambdateam.mycar.views.maintenance

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class MaintenanceDetailActivity : AppCompatActivity() {

    private val viewModel by viewModel<MaintenanceDetailViewModel>()

    private val ivClose by lazy { findViewById<ImageView>(R.id.iv_maintenance_detail_close) }
    private val tvDate by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_date) }
    private val tvVehicle by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_vehicle) }
    private val tvKm by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_km) }
    private val tvType by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_type) }
    private val tvComponent by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_component) }
    private val tvAmount by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_amount) }
    private val tvDelete by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_delete) }
    private val loading by lazy { findViewById<LinearLayout>(R.id.ll_maintenance_detail_loading) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance_detail)

        getParcelable()
        setListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.maintenance.observe(this) { fillScreenValues(it) }
        viewModel.viewState.observe(this) { handleViewState(it) }
        viewModel.isSuccessDelete.observe(this) {
            if (it) {
                val data = Intent()
                data.putExtra(AddMaintenanceActivity.RESULT_KEY, true)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }

    private fun setListeners() {
        ivClose.setOnClickListener {
            finish()
        }
        tvDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação de exlusão")
        builder.setMessage("Você tem certeza que quer apagar esse item?")
        builder.setPositiveButton("Sim") { dialog, which ->
            viewModel.deleteMaintenance()
        }
        builder.setNegativeButton("Não", null)
        builder.show()
    }

    private fun getParcelable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(MAINTENANCE_ITEM, MaintenancePresentModel::class.java)?.let {
                viewModel.setMaintenance(it)
            }
        } else {
            intent.getParcelableExtra<MaintenancePresentModel>(MAINTENANCE_ITEM)?.let {
                viewModel.setMaintenance(it)
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

    private fun handleViewState(it: ViewState?) {
        when (it) {
            ViewState.Loading -> {
                loading.visibility = View.VISIBLE
            }

            ViewState.Error -> {
                handleViewState(ViewState.HideLoading)
                showErrorAlert()
            }

            ViewState.HideLoading -> {
                delay {
                    loading.visibility = View.GONE
                }
            }

            else -> {}
        }
    }

    private fun showErrorAlert() {
        val alertDialogBuilder = android.app.AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Ops...")
        alertDialogBuilder.setMessage("Tivemos um problema ao apagar a manutenção, verifique sua conexão e tente novamente.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
        private const val MAINTENANCE_ITEM = "MAINTENANCE_ITEM"
        fun getIntentLauncher(context: Context, maintenance: MaintenancePresentModel) =
            Intent(context, MaintenanceDetailActivity::class.java).apply {
                putExtra(MAINTENANCE_ITEM, maintenance)
            }
    }
}