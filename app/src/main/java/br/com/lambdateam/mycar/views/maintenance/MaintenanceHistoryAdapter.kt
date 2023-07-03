package br.com.lambdateam.mycar.views.maintenance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.maintenance.MaintenancePresentModel

class MaintenanceHistoryAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemSelected: ((maintenance: MaintenancePresentModel) -> Unit)? = null

    private var items: List<MaintenancePresentModel> = listOf()
    private var recoveryItems = listOf<MaintenancePresentModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.maintenance_history_item_list, parent, false)
        return MaintenanceItem(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? MaintenanceItem)?.bind(items[position])
    }

    fun setItems(items: List<MaintenancePresentModel>) {
        this.items = items
        this.recoveryItems = items
        notifyItemRangeChanged(0, items.size)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun search(text: String) {
        if (text.isEmpty()) {
            this.items = this.recoveryItems
            notifyItemRangeChanged(0, recoveryItems.size)
            return
        }
        val filter = items.filter {
            it.vehicle?.contains(text) == true ||
                    it.km?.contains(text) == true ||
                    it.amount?.contains(text) == true ||
                    it.manufacturer?.contains(text) == true ||
                    it.component?.contains(text) == true ||
                    it.maintenanceType?.contains(text) == true ||
                    it.maintenanceDate?.contains(text) == true
        }
        if (filter.isNotEmpty()) {
            this.items = filter
            notifyDataSetChanged()
        } else {
            this.items = filter
            notifyDataSetChanged()
        }
    }

    inner class MaintenanceItem(val view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_title) }
        private val tvDate by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_date) }
        private val tvKm by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_km) }
        private val tvType by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_type) }
        private val tvComponent by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_component) }
        private val tvAmount by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_amount) }

        fun bind(item: MaintenancePresentModel) {
            view.setOnClickListener { onItemSelected?.invoke(item) }
            tvTitle.text = item.vehicle
            tvDate.text = item.maintenanceDate
            tvKm.text = item.km
            tvType.text = item.maintenanceType
            tvComponent.text = item.component
            tvAmount.text = item.amount
        }
    }
}