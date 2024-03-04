package com.agree.ecosystem.core.utils.utility.extension

import android.animation.ObjectAnimator
import android.content.res.ColorStateList
import android.graphics.ColorFilter
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDrawable
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.SimpleColorFilter
import com.airbnb.lottie.model.KeyPath
import com.airbnb.lottie.value.LottieValueCallback
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialSharedAxis
import com.telkom.legion.component.radio.base.LgnRadioButton
import com.telkom.legion.component.radio.base.LgnRadioContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@ColorInt
fun View.materialColor(@AttrRes res: Int): Int = MaterialColors.getColor(this, res)

fun ViewGroup.prepareAnim(isSharedAxis: Boolean = false, axis: Int = MaterialSharedAxis.Z) {
    TransitionManager.endTransitions(this)
    if (isSharedAxis)
        TransitionManager.beginDelayedTransition(
            this,
            MaterialSharedAxis(axis, true).apply {
                duration = 325L
                interpolator = DecelerateInterpolator()
            }
        )
    else
        TransitionManager.beginDelayedTransition(
            this,
            MaterialFade().apply {
                duration = 325L
                interpolator = DecelerateInterpolator()
            }
        )
}

fun LottieAnimationView.tint(
    @ColorInt color: Int
) {
    val filter = SimpleColorFilter(color)
    val keyPath = KeyPath("**")
    val callback: LottieValueCallback<ColorFilter> = LottieValueCallback(filter)
    addValueCallback(keyPath, LottieProperty.COLOR_FILTER, callback)
}

fun <T : View> T.delayOnLifecycle(
    durationInMillis: Long,
    block: T.() -> Unit
) =
    findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.Main.immediate) {
        delay(durationInMillis)
        block.invoke(this@delayOnLifecycle)
    }

fun RecyclerView.setItemSeparator() {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    ContextCompat.getDrawable(context, UiKitDrawable.separator_default)
        ?.let { divider.setDrawable(it) }
    addItemDecoration(divider)
}

fun ChipGroup.addChip(
    label: String,
    @AttrRes textColor: Int,
    @AttrRes backgroundColor: Int,
    onClick: (() -> Unit)? = null
) {
    Chip(context).apply {
        id = View.generateViewId()
        text = label
        isClickable = onClick != null
        isCheckable = onClick != null
        isCheckedIconVisible = false
        isFocusable = onClick != null
        textStartPadding = 20F
        textEndPadding = 20F
        setTextAppearance(UiKitAttrs.captionLargeSemiBold.getAttrsValue(context))
        setTextColor(textColor.getAttrsValue(context))
        chipBackgroundColor = ColorStateList.valueOf(backgroundColor.getAttrsValue(context))
        chipStrokeColor = ColorStateList.valueOf(textColor.getAttrsValue(context))
        chipStrokeWidth = 2F
        setOnClickListener { onClick?.invoke() }
        addView(this)
    }
}

fun TextView.expand() {
    val animation = ObjectAnimator.ofInt(this, "maxLines", this.lineCount)
    animation.setDuration(200L).start()
}

fun TextView.collapse(numLines: Int? = null) {
    val animation = ObjectAnimator.ofInt(this, "maxLines", numLines ?: 4)
    animation.setDuration(200L).start()
}

fun LgnRadioContainer.checkByTag(tag: String) {
    var isFound = false
    for (i in 0 until radioGroup.childCount) {
        val radio = radioGroup.getChildAt(i) as LgnRadioButton
        if (radio.tag.toString().lowercase() == tag.lowercase()) {
            radioGroup.check(radio.id)
            isFound = true
            break
        }
    }
    if (!isFound && this.isRequired) this.radioGroup.check(0)
}

fun LgnRadioContainer.checkByText(text: String) {
    var isFound = false
    for (i in 0 until radioGroup.childCount) {
        val radio = radioGroup.getChildAt(i) as LgnRadioButton
        if (radio.text.toString().lowercase() == text.lowercase()) {
            radioGroup.check(radio.id)
            isFound = true
            break
        }
    }
    if (!isFound && this.isRequired) this.radioGroup.check(0)
}

fun LgnRadioContainer.checkByPosition(position: Int) {
    var isFound = false
    for (i in 0 until radioGroup.childCount) {
        if (i == position) {
            val radio = radioGroup.getChildAt(i) as LgnRadioButton
            radioGroup.check(radio.id)
            isFound = true
            break
        }
    }
    if (!isFound && this.isRequired) this.radioGroup.check(0)
}

fun LgnRadioContainer.getCheckedTag(): String {
    val selectedId = radioGroup.checkedRadioButtonId
    val radioButton = radioGroup.findViewById<LgnRadioButton>(selectedId)
    return radioButton.tag.toString()
}

fun LgnRadioContainer.getCheckedText(): CharSequence {
    val selectedId = radioGroup.checkedRadioButtonId
    val radioButton = radioGroup.findViewById<LgnRadioButton>(selectedId)
    return radioButton.text
}

fun LgnRadioContainer.getCheckedPosition(): Int {
    val selectedId = radioGroup.checkedRadioButtonId
    val radioButton = radioGroup.findViewById<LgnRadioButton>(selectedId)
    return radioGroup.indexOfChild(radioButton)
}
