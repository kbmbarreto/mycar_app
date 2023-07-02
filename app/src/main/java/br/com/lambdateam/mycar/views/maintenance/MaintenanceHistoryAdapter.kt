package br.com.lambdateam.mycar.views.maintenance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.maintenance.Maintenance
import br.com.lambdateam.mycar.model.utils.convertDateFormat
import br.com.lambdateam.mycar.model.utils.convertToCurrencyFormat

class MaintenanceHistoryAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Maintenance> = listOf()
    private var recoveryItems = listOf<Maintenance>()

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
            it.vehicle?.description?.contains(text) == true ||
                    it.km.toString().contains(text) ||
                    it.amount.toString().contains(text) ||
                    it.manufacturer?.manufacturer.toString().contains(text) ||
                    it.vehicle?.description?.contains(text) == true ||
                    it.component?.component?.contains(text) == true ||
                    it.maintenanceType?.maintenanceType?.contains(text) == true ||
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

        fun bind(item: Maintenance) {
            tvTitle.text = item.vehicle?.description
            tvDate.text = convertDateFormat(item.maintenanceDate.toString())
            tvKm.text =
                StringBuilder(item.km.toString()).append(view.context.getString(R.string.km))
            tvType.text = item.maintenanceType?.maintenanceType
            tvComponent.text = item.component?.component
            item.amount?.let {
                tvAmount.text = convertToCurrencyFormat(it)
            }
        }
    }
}