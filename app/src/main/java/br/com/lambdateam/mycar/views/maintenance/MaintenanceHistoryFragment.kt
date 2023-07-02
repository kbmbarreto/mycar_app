package br.com.lambdateam.mycar.views.maintenance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.utils.ViewState
import org.koin.android.ext.android.inject

class MaintenanceHistoryFragment : Fragment() {

    private lateinit var rvMaintenance: RecyclerView
    private lateinit var vfMaintenance: ViewFlipper
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
        viewModel.getMaintenances()
    }

    private fun setupListeners() {
        viewModel.maintenances.observe(viewLifecycleOwner) { list ->
            list?.let {
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
        rvMaintenance.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = maintenanceAdapter
        }
    }

    private fun initViews() {
        view?.let {
            rvMaintenance = it.findViewById(R.id.rv_maintenance_history)
            vfMaintenance = it.findViewById(R.id.vf_maintenance_history)
        }
    }
}