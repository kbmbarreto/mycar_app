package br.com.lambdateam.mycar.model.utils

import android.os.CountDownTimer
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged

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