package com.agree.ecosystem.finances.utils

import android.text.Editable
import android.text.TextWatcher
import com.telkom.legion.component.textfield.LgnSingleField
import timber.log.Timber
import java.text.NumberFormat
import java.util.Locale

fun formatCurrency(number: Double): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getNumberInstance(localeID)
    return numberFormat.format(number)
}

fun myTimber(tag: String = "DATAAA", value: Any) {
    Timber.tag(tag).v(value.toString())
}

fun LgnSingleField.addCurrencyTextWatcher() {
    val et = this.editText
    val that = this

    val watcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            et?.let {
                et.removeTextChangedListener(this)
                if (s.toString() != "") {
                    if (s.toString().substring(0, 1) == "0") {
                        if (s.toString().length > 2) {
                            if (s.toString().substring(0, 1) == "0" && s.toString()
                                    .substring(1, 2) !== "0"
                            ) {
                                if (s.toString().substring(1) === ".") {
                                    et.setText("0")
                                } else {
                                    et.setText(
                                        formatCurrency("0".toDouble())
                                    )
                                }
                            } else if (s.toString().substring(0, 1) != "0") {
                                et.setText(formatCurrency(s.toString().toDouble()))
                            } else {
                                et.setText("0")
                                that.placeHolder = ""
                            }
                        } else {
                            et.setText(formatCurrency(s.toString().toDouble()))
                        }
                    } else {
                        val cleanString = s.toString().replace(".", "")
                        et.setText(formatCurrency(cleanString.toDouble()))
                    }
                } else {
                    et.setText("0")
                    that.placeHolder = ""
                }

                et.setSelection(et.length())
                et.addTextChangedListener(this)
            }
        }

        override fun afterTextChanged(p0: Editable?) {
        }
    }

    et?.addTextChangedListener(watcher)
}