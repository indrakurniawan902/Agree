package com.agree.ecosystem.core.utils.utility.extension

import com.agree.ui.UiColors

fun String.containsAll(vararg string: String): Boolean {
    string.forEach {
        if (this.contains(it)) {
            return true
        }
    }
    return false
}

fun String.getSectorColor(): Int {
    return when (this) {
        "peternakan" -> UiColors.agl_normal
        "pertanian" -> UiColors.primary_500
        "perikanan" -> UiColors.aqf_normal
        else -> UiColors.primary_500
    }
}
