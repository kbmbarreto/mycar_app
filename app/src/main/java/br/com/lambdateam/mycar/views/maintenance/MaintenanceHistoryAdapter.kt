package br.com.lambdateam.mycar.views.maintenance

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.maintenance.Maintenance

class MaintenanceHistoryAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Maintenance> = listOf()

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

    fun setItems(items: List<Maintenance>) {
        this.items = items
        notifyItemRangeChanged(0, items.size)
    }

    inner class MaintenanceItem(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTitle by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_title) }
        private val tvDate by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_date) }
        private val tvKm by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_km) }
        private val tvType by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_type) }
        private val tvComponent by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_component) }
        private val tvAmount by lazy { view.findViewById<TextView>(R.id.tv_maintenance_history_amount) }

        fun bind(item: Maintenance) {
            tvTitle.text = item.vehicle?.description
            tvDate.text = item.maintenanceDate
            tvKm.text = item.km.toString()
            tvType.text = item.maintenanceType?.maintenanceType
            tvComponent.text = item.component?.component
            tvAmount.text = item.amount.toString()
        }
    }
}