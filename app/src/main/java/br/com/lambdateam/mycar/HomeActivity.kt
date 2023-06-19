package br.com.lambdateam.mycar

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var menuButton: ImageButton
    private lateinit var historyButton: Button
    private lateinit var ticketsButton: Button
    private lateinit var componentsButton: Button
    private lateinit var vehiclesButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        menuButton = findViewById(R.id.menu_button)
//        historyButton = findViewById(R.id.history_button)
//        ticketsButton = findViewById(R.id.tickets_button)
//        componentsButton = findViewById(R.id.components_button)
//        vehiclesButton = findViewById(R.id.vehicles_button)
//
//        menuButton.setOnClickListener {
//            // Lógica para abrir/fechar o menu
//            Toast.makeText(this, "Menu sanduíche clicado", Toast.LENGTH_SHORT).show()
//        }
//
//        historyButton.setOnClickListener {
//            // Lógica para o botão Histórico de manutenções
//            Toast.makeText(this, "Histórico de manutenções clicado", Toast.LENGTH_SHORT).show()
//        }
//
//        ticketsButton.setOnClickListener {
//            // Lógica para o botão Multas
//            Toast.makeText(this, "Multas clicado", Toast.LENGTH_SHORT).show()
//        }
//
//        componentsButton.setOnClickListener {
//            // Lógica para o botão Componentes
//            Toast.makeText(this, "Componentes clicado", Toast.LENGTH_SHORT).show()
//        }
//
//        vehiclesButton.setOnClickListener {
//            // Lógica para o botão Veículos
//            Toast.makeText(this, "Veículos clicado", Toast.LENGTH_SHORT).show()
//        }
    }
}
