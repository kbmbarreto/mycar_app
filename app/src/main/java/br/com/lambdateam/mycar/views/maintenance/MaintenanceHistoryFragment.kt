package br.com.lambdateam.mycar.views.maintenance

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.afterTextChangedDelayed
import br.com.lambdateam.mycar.views.maintenance.AddMaintenanceActivity.Companion.RESULT_KEY
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject


class MaintenanceHistoryFragment : Fragment() {

    private lateinit var rvMaintenance: RecyclerView
    private lateinit var vfMaintenance: ViewFlipper
    private lateinit var tvCount: TextView
    private lateinit var ivCleanSearch: ImageView
    private lateinit var etSearch: EditText
    private lateinit var btAdd: CardView
    private lateinit var tvAddEmpty: TextView
    private val maintenanceAdapter = MaintenanceHistoryAdapter()
    private val viewModel by inject<MaintenanceViewModel>()

    private val addMaintenanceLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            res.data?.getBooleanExtra(RESULT_KEY, false)?.let {
                if (it) {
                    showSuccessSnackBar("Manutenção adicionada com sucesso!")
                    viewModel.updateMaintenances()
                }
            }
        }
    }

    private val maintenanceDetailLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { res ->
        if (res.resultCode == Activity.RESULT_OK) {
            res.data?.getBooleanExtra(MaintenanceDetailActivity.RESULT_KEY, false)?.let {
                if (it) {
                    showSuccessSnackBar("Manutenção excluída com sucesso!")
                    viewModel.updateMaintenances()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maintenance_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        setupList()
        setupListeners()
        setupSearch()
        viewModel.getMaintenances()
    }

    private fun setupSearch() {
        etSearch.afterTextChangedDelayed {
            maintenanceAdapter.search(it)
            tvCount.text = resources.getQuantityString(
                R.plurals.result_plurals,
                maintenanceAdapter.itemCount,
                maintenanceAdapter.itemCount
            )
            ivCleanSearch.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        }
        ivCleanSearch.setOnClickListener {
            etSearch.apply {
                setText("")
                clearFocus()
            }
            it.visibility = View.GONE
            closeKeyboard()
        }
        btAdd.setOnClickListener {
            addMaintenanceLauncher.launch(AddMaintenanceActivity.getIntentLauncher(requireContext()))
        }
        tvAddEmpty.setOnClickListener {
            addMaintenanceLauncher.launch(AddMaintenanceActivity.getIntentLauncher(requireContext()))
        }
    }

    private fun closeKeyboard() {
        val imm: InputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun setupListeners() {
        viewModel.maintenances.observe(viewLifecycleOwner) { list ->
            list?.let {
                tvCount.text =
                    resources.getQuantityString(R.plurals.result_plurals, it.size, it.size)
                maintenanceAdapter.setItems(it)
            }
        }
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleViewState(it)
        }
    }

    private fun showSuccessSnackBar(message: String) {
        view?.let {
            val snackBar =
                Snackbar.make(it, message, Snackbar.LENGTH_LONG)
            snackBar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.green))
            snackBar.setActionTextColor(Color.WHITE)
            snackBar.show()
        }
    }

    private fun handleViewState(it: ViewState?) {
        when (it) {
            ViewState.Loading -> {
                vfMaintenance.displayedChild = 1
            }

            ViewState.HideLoading -> {
                vfMaintenance.displayedChild = 0
            }

            ViewState.Empty, ViewState.Error -> {
                vfMaintenance.displayedChild = 2
            }

            else -> {}
        }
    }

    private fun setupList() {
        maintenanceAdapter.onItemSelected = {
            maintenanceDetailLauncher.launch(
                MaintenanceDetailActivity.getIntentLauncher(
                    requireContext(),
                    it
                )
            )
        }
        rvMaintenance.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = maintenanceAdapter
        }
    }

    private fun initViews() {
        view?.let {
            rvMaintenance = it.findViewById(R.id.rv_maintenance_history)
            vfMaintenance = it.findViewById(R.id.vf_maintenance_history)
            etSearch = it.findViewById(R.id.et_maintenance_history_search)
            tvCount = it.findViewById(R.id.tv_maintenance_history_count)
            ivCleanSearch = it.findViewById(R.id.iv_maintenance_history_clean_search)
            btAdd = it.findViewById(R.id.iv_maintenance_history_add)
            tvAddEmpty = it.findViewById(R.id.tv_maintenance_history_empty_add)
        }
    }
}