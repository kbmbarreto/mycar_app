package br.com.lambdateam.mycar.views

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import br.com.lambdateam.mycar.R

class MainActivity : AppCompatActivity() {

    private val navHostFragment by lazy { supportFragmentManager.findFragmentById(R.id.fc_home_menu) as NavHostFragment }
    private val menuButton by lazy { findViewById<ImageButton>(R.id.menu_button) }
    private val title by lazy { findViewById<TextView>(R.id.title_textview) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupMenuButtonAction()
        setupNavigation()
    }

    private fun setupMenuButtonAction() {
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
    }

    private fun setupNavigation() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.nav_home_fragment -> {
                    title.text = getString(R.string.app_name)
                    menuButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_menu))
                    setupMenuButtonAction()
                }
                R.id.nav_maintenance_history_fragment -> {
                    title.text = getString(R.string.maintenances)
                    menuButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_home))
                    menuButton.setOnClickListener {
                        navHostFragment.navController.navigate(R.id.nav_home_fragment)
                    }
                }
            }
        }
    }
}