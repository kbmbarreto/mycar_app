package br.com.lambdateam.mycar.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.lambdateam.mycar.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val historyCard = view.findViewById<CardView>(R.id.history_maintenances)
        val servicesCard = view.findViewById<CardView>(R.id.services)
        val trafficTicketCard = view.findViewById<CardView>(R.id.history_traffic_ticket)
        val vehiclesCard = view.findViewById<CardView>(R.id.vehicles)

        historyCard.setOnClickListener {
            findNavController().navigate(R.id.nav_maintenance_history_fragment)
        }

        servicesCard.setOnClickListener {
            Toast.makeText(requireContext(), "Em implementação", Toast.LENGTH_SHORT).show()
        }

        trafficTicketCard.setOnClickListener {
            Toast.makeText(requireContext(), "Em implementação", Toast.LENGTH_SHORT).show()
        }

        vehiclesCard.setOnClickListener {
            Toast.makeText(requireContext(), "Em implementação", Toast.LENGTH_SHORT).show()
        }
    }
}