package br.com.lambdateam.mycar.views.maintenance

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.delay
import com.google.android.material.snackbar.Snackbar
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
    private val tvEdit by lazy { findViewById<TextView>(R.id.tv_maintenance_detail_edit) }

    private var maintenanceChanged = false

    private val editMaintenanceLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            res.data?.getBooleanExtra(AddMaintenanceActivity.RESULT_KEY, false)?.let {
                if (it) {
                    maintenanceChanged = true
                    showSnackBarMessage("Manutenção editada com sucesso!", R.color.green)
                    viewModel.fetchMaintenanceDetail()
                }
            }
        }
    }

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
            if (maintenanceChanged) {
                val data = Intent()
                data.putExtra(SHOULD_UPDATE_LIST, true)
                setResult(Activity.RESULT_OK, data)
                finish()
            } else finish()
        }
        tvDelete.setOnClickListener {
            showDeleteConfirmationDialog()
        }
        tvEdit.setOnClickListener {
            editMaintenanceLauncher.launch(
                AddMaintenanceActivity.getIntentLauncher(
                    this,
                    true
                )
            )
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

    private fun showSnackBarMessage(message: String, @ColorRes color: Int) {
        val snackBar =
            Snackbar.make(
                findViewById(R.id.rr_maintenance_detail_container),
                message,
                Snackbar.LENGTH_LONG
            )
        snackBar.setBackgroundTint(ContextCompat.getColor(this, color))
        snackBar.setActionTextColor(Color.WHITE)
        snackBar.show()
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

            is ViewState.Error -> {
                handleViewState(ViewState.HideLoading)
                if (it.message != null) {
                    showSnackBarMessage(it.message, R.color.red)
                } else {
                    showErrorAlert()
                }
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
        const val SHOULD_UPDATE_LIST = "SHOULD_UPDATE_LIST"
        private const val MAINTENANCE_ITEM = "MAINTENANCE_ITEM"
        fun getIntentLauncher(context: Context, maintenance: MaintenancePresentModel) =
            Intent(context, MaintenanceDetailActivity::class.java).apply {
                putExtra(MAINTENANCE_ITEM, maintenance)
            }
    }
}