package com.agree.ecosystem.core.utils.utility.extension

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

fun Double.toCurrencyFormat(maximumFractionDigits: Int = 0): String {
    var fractionDigits: String
    if (maximumFractionDigits > 0) {
        fractionDigits = "."
        for (i in 1..maximumFractionDigits) fractionDigits += "0"
    } else {
        fractionDigits = ".-"
    }

    val otherSymbols = DecimalFormatSymbols(Locale.getDefault())
    otherSymbols.decimalSeparator = ','
    otherSymbols.groupingSeparator = '.'

    val formatter1 = DecimalFormat("#,###$fractionDigits", otherSymbols)
    return formatter1.format(this)
}
