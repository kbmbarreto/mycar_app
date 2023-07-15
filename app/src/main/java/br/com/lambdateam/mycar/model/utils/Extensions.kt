package br.com.lambdateam.mycar.model.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.CountDownTimer
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.EditText
import android.widget.Spinner
import androidx.core.widget.doAfterTextChanged
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
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

fun delay(time: Long = 500, run: () -> Unit) {
    object : CountDownTimer(time, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            run()
        }
    }.start()
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

@SuppressLint("SimpleDateFormat")
fun getStringDate(timestamp: Long, pattern: String): String {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp
    return SimpleDateFormat(pattern).format(timestamp)
}

fun View.closeKeyboard() {
    val imm: InputMethodManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Spinner.setOnItemSelected(run: (position: Int) -> Unit) {
    this.onItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            run(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    }
}