package br.com.lambdateam.mycar

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val menuButton = findViewById<ImageButton>(R.id.menu_button)
        val historyCard = findViewById<CardView>(R.id.history_maintenances)
        val servicesCard = findViewById<CardView>(R.id.services)
        val trafficTicketCard = findViewById<CardView>(R.id.history_traffic_ticket)
        val vehiclesCard = findViewById<CardView>(R.id.vehicles)

        menuButton.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.inflate(R.menu.menu_main)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_oficinas -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_veiculos -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_multas -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_lista_compras -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_servicos -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_fabricantes -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_componentes -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_historico_manutencoes -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_lgpd -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.menu_sair -> {
                        Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
                        true
                    }

                    else -> false
                }
            }

            popupMenu.show()
        }

        historyCard.setOnClickListener {
            Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
        }

        servicesCard.setOnClickListener {
            Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
        }

        trafficTicketCard.setOnClickListener {
            Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
        }

        vehiclesCard.setOnClickListener {
            Toast.makeText(this, "Em implementação", Toast.LENGTH_SHORT).show()
        }
    }
}