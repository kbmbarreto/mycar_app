package br.com.lambdateam.mycar.views.maintenance

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.afterTextChangedDelayed
import org.koin.android.ext.android.inject

class MaintenanceHistoryFragment : Fragment() {

    private lateinit var rvMaintenance: RecyclerView
    private lateinit var vfMaintenance: ViewFlipper
    private lateinit var tvCount: TextView
    private lateinit var ivCleanSearch: ImageView
    private lateinit var etSearch: EditText
    private val maintenanceAdapter = MaintenanceHistoryAdapter()
    private val viewModel by inject<MaintenanceViewModel>()

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
            MaintenanceDetailActivity.start(requireContext(), it)
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
        }
    }
}