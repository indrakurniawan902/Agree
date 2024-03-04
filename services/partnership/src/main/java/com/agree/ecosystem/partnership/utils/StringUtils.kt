package com.agree.ecosystem.partnership.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.agree.ui.UiColors
import java.util.*

fun String.customLinkSpan(context: Context, dataSpannable: DataSpannable): SpannableString {
    val sentence = SpannableString(this)

    dataSpannable.data.let {
        it.forEach { value ->
            sentence.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        value.listener.invoke()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = ContextCompat.getColor(context, UiColors.success_400)
                        ds.isUnderlineText = false
                        ds.isFakeBoldText = true
                    }
                },
                value.firstChar, value.lastChar, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    return sentence
}

fun String.capitalize() = lowercase().replaceFirstChar {
    it.titlecase(Locale.getDefault())
}

fun String.capitalizeWords() = split(" ").joinToString(" ") {
    if (it != "DKI" && it != "DI") {
        it.capitalize()
    } else {
        it
    }
}
