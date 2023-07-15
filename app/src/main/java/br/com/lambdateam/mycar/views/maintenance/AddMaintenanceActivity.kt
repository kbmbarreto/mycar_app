package br.com.lambdateam.mycar.views.maintenance

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.delay
import br.com.lambdateam.mycar.model.utils.getStringDate
import br.com.lambdateam.mycar.model.utils.setOnItemSelected
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Calendar


class AddMaintenanceActivity : AppCompatActivity() {

    private val loading by lazy { findViewById<LinearLayout>(R.id.ll_add_maintenance_loading) }
    private val spType by lazy { findViewById<Spinner>(R.id.sp_add_maintenance_type) }
    private val spVehicle by lazy { findViewById<Spinner>(R.id.sp_add_maintenance_vehicle) }
    private val spComponent by lazy { findViewById<Spinner>(R.id.sp_add_maintenance_component) }
    private val spManufacturer by lazy { findViewById<Spinner>(R.id.sp_add_maintenance_manufacturer) }
    private val ivClose by lazy { findViewById<ImageView>(R.id.iv_add_maintenance_close) }
    private val etKm by lazy { findViewById<EditText>(R.id.et_add_maintenance_km) }
    private val etNextKm by lazy { findViewById<EditText>(R.id.et_add_maintenance_next_km) }
    private val etAmount by lazy { findViewById<EditText>(R.id.et_add_maintenance_amount) }
    private val btAdd by lazy { findViewById<Button>(R.id.bt_add_maintenance_add) }
    private val tvSelectDate by lazy { findViewById<TextView>(R.id.tv_add_maintenance_select_date) }
    private val tvDate by lazy { findViewById<TextView>(R.id.tv_add_maintenance_date) }

    private val viewModel by viewModel<AddMaintenanceViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_maintenance)

        setupObservers()
        setupListeners()
        onViewLoaded()
    }

    private fun setupListeners() {
        ivClose.setOnClickListener { finish() }
        etKm.doAfterTextChanged {
            viewModel.setKm(it.toString())
        }
        etNextKm.doAfterTextChanged {
            viewModel.setNextKm(it.toString())
        }
        etAmount.doAfterTextChanged {
            viewModel.setAmount(it.toString())
        }
        tvSelectDate.setOnClickListener {
            showDatePicker()
        }
        tvDate.setOnClickListener {
            showDatePicker()
        }
        btAdd.setOnClickListener {
            viewModel.createMaintenance()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val calendarYear = calendar.get(Calendar.YEAR)
        val calendarMonth = calendar.get(Calendar.MONTH)
        val calendarDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance()
                selectedCalendar.set(Calendar.YEAR, year)
                selectedCalendar.set(Calendar.MONTH, month)
                selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val timestamp = selectedCalendar.timeInMillis
                val backendDate = getStringDate(timestamp, "yyyy-MM-dd")
                val presentDate = getStringDate(timestamp, "dd/MM/yyyy")
                tvDate.apply {
                    text = presentDate
                    visibility = View.VISIBLE
                }
                tvSelectDate.visibility = View.GONE
                viewModel.setDate(backendDate)
            },
            calendarYear,
            calendarMonth,
            calendarDay
        )
        datePickerDialog.show()
    }

    private fun showErrorAlert() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Ops...")
        alertDialogBuilder.setMessage("Tivemos um problema ao adicionar a manutenção, verifique sua conexão e tente novamente.")
        alertDialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }


    private fun setupObservers() {
        viewModel.viewState.observe(this) { handleViewState(it) }
        viewModel.maintenanceTypes.observe(this) {
            val spinnerItems =
                it.map { item -> item.maintenanceType }.filter { item -> !item.isNullOrEmpty() }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spType.adapter = adapter
            spType.setOnItemSelected { position -> viewModel.setType(position) }
        }
        viewModel.maintenanceVehicles.observe(this) {
            val spinnerItems =
                it.map { item -> item.description }.filter { item -> !item.isNullOrEmpty() }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spVehicle.adapter = adapter
            spVehicle.setOnItemSelected { position -> viewModel.setVehicle(position) }
        }
        viewModel.maintenanceComponents.observe(this) {
            val spinnerItems =
                it.map { item -> item.component }.filter { item -> !item.isNullOrEmpty() }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spComponent.adapter = adapter
            spComponent.setOnItemSelected { position -> viewModel.setComponent(position) }
        }
        viewModel.maintenanceManufacturers.observe(this) {
            val spinnerItems =
                it.map { item -> item.manufacturer }.filter { item -> !item.isNullOrEmpty() }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerItems)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spManufacturer.adapter = adapter
            spManufacturer.setOnItemSelected { position -> viewModel.setManufacturer(position) }
        }
        viewModel.isButtonEnabled.observe(this) {
            btAdd.isEnabled = it
        }
        viewModel.isSuccess.observe(this) {
            if (it) {
                val data = Intent()
                data.putExtra(RESULT_KEY, true)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
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

    private fun onViewLoaded() {
        viewModel.onViewLoaded()
    }

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
        fun getIntentLauncher(context: Context) =
            Intent(context, AddMaintenanceActivity::class.java)
    }
}