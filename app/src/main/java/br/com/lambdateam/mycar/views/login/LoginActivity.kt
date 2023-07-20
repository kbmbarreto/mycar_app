package br.com.lambdateam.mycar.views.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import br.com.lambdateam.mycar.R
import br.com.lambdateam.mycar.model.utils.ViewState
import br.com.lambdateam.mycar.model.utils.closeKeyboard
import br.com.lambdateam.mycar.model.utils.delay
import br.com.lambdateam.mycar.views.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private val loginButton: Button by lazy { findViewById(R.id.buttonLogin) }
    private val etEmail: EditText by lazy { findViewById(R.id.editTextEmail) }
    private val etPassword: EditText by lazy { findViewById(R.id.editTextPassword) }
    private val loading: LinearLayout by lazy { findViewById(R.id.ll_login_loading) }

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewModel.isUserAuthenticated.observe(this) {
            if (it) {
                openMainActivity()
            }
        }
        viewModel.viewState.observe(this) {
            handleViewState(it)
        }

        viewModel.loginResponse.observe(this) {
            delay {
                cleanFields()
                openMainActivity()
            }
        }
    }

    private fun handleViewState(it: ViewState?) {
        when (it) {
            ViewState.Loading -> {
                loading.visibility = View.VISIBLE
            }

            ViewState.HideLoading -> {
                delay {
                    loading.visibility = View.GONE
                }
            }

            is ViewState.Error -> {
                handleViewState(ViewState.HideLoading)
                Toast.makeText(
                    this,
                    getString(R.string.login_error_description),
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }
    }

    private fun cleanFields() {
        etEmail.setText("")
        etPassword.setText("")
    }

    private fun setupListeners() {
        loginButton.setOnClickListener {
            viewModel.login()
            it.closeKeyboard()
        }
        etEmail.doAfterTextChanged {
            viewModel.setEmail(it.toString())
        }
        etPassword.doAfterTextChanged {
            viewModel.setPassword(it.toString())
        }
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}