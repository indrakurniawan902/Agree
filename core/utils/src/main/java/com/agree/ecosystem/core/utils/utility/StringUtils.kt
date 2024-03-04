package com.agree.ecosystem.core.utils.utility

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.agree.ecosystem.core.utils.domain.reqres.model.DataSpannable
import com.agree.ui.UiKitAttrs
import com.telkom.legion.component.utility.extension.requiredColor
import timber.log.Timber
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale
import java.util.regex.Pattern

@Deprecated(message = "Please using new method to create link span!")
fun String.customLinkSpan(
    context: Context,
    dataSpannable: DataSpannable,
    isUnderline: Boolean = false
): SpannableString {
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
                        ds.color = context.requiredColor(UiKitAttrs.colorPrimary)
                        ds.isUnderlineText = isUnderline
                        ds.isFakeBoldText = true
                    }
                },
                value.firstChar, value.lastChar, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    return sentence
}

/**
 * Create Single Link Span helper,
 * this method will find and serialize <a>text</a> and will add callback from
 * detected anchor text tags
 */
fun String.singleLinkSpan(context: Context, action: () -> Unit = {}): SpannableString {
    val serialisedString = serializeString()
    val sentence = SpannableString(serialisedString.first)
    serialisedString.second.createSingleSpan(serialisedString.first, action).data.forEach { value ->
        sentence.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    value.listener.invoke()
                }

                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.color = context.requiredColor(UiKitAttrs.colorPrimary)
                    ds.isUnderlineText = true
                    ds.isFakeBoldText = true
                }
            },
            value.firstChar, value.lastChar, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    return sentence
}

fun String.customLinkSpan(context: Context, dataSpannable: DataSpannable): SpannableString {
    val sentence = SpannableString.valueOf(this)
    dataSpannable.data.let {
        it.forEach { value ->
            sentence.setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        value.listener.invoke()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = context.requiredColor(UiKitAttrs.colorPrimary)
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

/**
 * Find Anchor Text using regex and will return
 * pair of serialized text and list of anchor text found
 */
internal fun String.serializeString(): Pair<String, List<String>> {
    val finder = Pattern.compile("(\\<a>)(.*?)(\\</a>)")
        .matcher(this)
    val listMatcher = mutableListOf<String>()
    val listWillRemoved = mutableListOf<String>()
    var newString = this

    try {
        /**
         * Find Anchor Text and Anchor Text Value using regex and add to list
         */
        while (finder.find()) {
            listWillRemoved.add(finder.group())
            listMatcher.add(finder.group(2).orEmpty())
        }

        /**
         * Serialize String from anchor text tag
         */
        listMatcher.forEachIndexed { index, string ->
            newString = newString.replace(listWillRemoved[index], string)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Timber.tag("SerializeString").e(e, "Error Found: %s", e.message)
    }

    return newString to listMatcher
}

internal fun String.findIndex(keyword: String): Pair<Int, Int>? {
    if (!this.contains(keyword)) {
        return null
    }
    return this.indexOf(keyword) to (this.indexOf(keyword) + keyword.length)
}

internal fun List<String>.createSingleSpan(paragraph: String, action: () -> Unit): DataSpannable {
    val filteredList =
        this.filter { paragraph.findIndex(it) != null }.map { paragraph.findIndex(it) }
    return DataSpannable(
        filteredList.map {
            DataSpannable.ListSpinnable(
                it?.first ?: 0,
                it?.second ?: 0,
                action
            )
        }
    )
}

fun String.setNumberFormatter(): String {
    val integerNum: String
    val decimalNum: String
    val formattedNumber: String

    val numbers: String = try {
        if (toBigDecimal().stripTrailingZeros().scale() <= 0) {
            toBigDecimal().stripTrailingZeros().setScale(0).toString()
        } else {
            toBigDecimal()
                .setScale(4, RoundingMode.HALF_UP)
                .stripTrailingZeros().toString()
        }
    } catch (error: NumberFormatException) {
        "0"
    }

    if (numbers.contains(".")) {
        integerNum = numbers.substringBefore(".")
        decimalNum = numbers.substringAfter(".")
        formattedNumber = "${NumberFormat.getNumberInstance(Locale.US).format(integerNum.toBigDecimal())
            .replace(",", ".")},$decimalNum"
    } else {
        formattedNumber = NumberFormat.getNumberInstance(Locale.US).format(numbers.toBigDecimal())
            .replace(",", ".")
    }
    return formattedNumber
}
