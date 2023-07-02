package br.com.lambdateam.mycar.model.utils

import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun EditText.afterTextChangedDelayed(delayTime: Long = 500, afterTextChanged: (String) -> Unit) {
    var timer: CountDownTimer? = null
    this.doAfterTextChanged {
        timer?.cancel()
        timer = object : CountDownTimer(delayTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                afterTextChanged.invoke(it.toString())
            }
        }.start()
    }
}

fun convertToCurrencyFormat(value: Double): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return currencyFormat.format(value)
}

fun convertDateFormat(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date: Date? = inputFormat.parse(inputDate)
    return date?.let { outputFormat.format(it) }.toString()
}