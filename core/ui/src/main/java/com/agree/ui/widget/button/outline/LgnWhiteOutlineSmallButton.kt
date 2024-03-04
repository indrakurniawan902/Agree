package com.agree.ui.widget.button.outline

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.agree.ui.R
import com.agree.ui.UiKitColors
import com.agree.ui.UiKitDrawable
import com.telkom.legion.component.button.outline.base.LgnSmallOutlineButton
import com.telkom.legion.component.utility.enums.ButtonColors

class LgnWhiteOutlineSmallButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LgnSmallOutlineButton(context, attrs, defStyleAttr) {
    override var colorBackground: ColorStateList? =
        ContextCompat.getColorStateList(context, R.color.color_button_outline_background_white)
        set(value) {
                binding.root.setCardBackgroundColor(value)
                binding.root.rippleColor = value
                field = value
            }

    override var colorText: ColorStateList? =
        ContextCompat.getColorStateList(context, R.color.color_button_outline_text_white)
        set(value) {
                field = value
                updateTextColor()
            }

    override var colorOutline: ColorStateList? =
        ContextCompat.getColorStateList(context, R.color.color_button_outline_stroke_white)
        set(value) {
                field = value
                binding.root.setStrokeColor(value)
            }

    override var progressBarDrawable: Drawable? =
        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_white)
        set(value) {
                field = value
                binding.pbButton.indeterminateDrawable = value
            }

    override var colorSet: ButtonColors = ButtonColors.DEFAULT
        set(value) {
            field = value
            when (value) {
                ButtonColors.DEFAULT -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        R.color.color_button_outline_background_white
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        R.color.color_button_outline_text_white
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        R.color.color_button_outline_stroke_white
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_white)
                }

                ButtonColors.PRIMARY -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_primary
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_primary
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_primary
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_primary)
                }

                ButtonColors.TERTIARY -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_tertiary
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_tertiary
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_tertiary
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_tertiary)
                }

                ButtonColors.SECONDARY -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_secondary
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_secondary
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_secondary
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_secondary)
                }

                ButtonColors.SUCCESS -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_success
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_success
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_success
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_success)
                }

                ButtonColors.ERROR -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_error
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_error
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_error
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_error)
                }

                ButtonColors.WARNING -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_warning
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_warning
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_warning
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_warning)
                }

                ButtonColors.INFO -> {
                    colorBackground = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_background_info
                    )
                    colorText = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_text_info
                    )
                    colorOutline = ContextCompat.getColorStateList(
                        context,
                        UiKitColors.color_button_outline_stroke_info
                    )
                    progressBarDrawable =
                        ContextCompat.getDrawable(context, UiKitDrawable.progress_view_info)
                }
            }
        }

    init {
        binding.pbButton.indeterminateDrawable = progressBarDrawable
        attrs?.let {
            val attr =
                context.obtainStyledAttributes(it, R.styleable.LgnWhiteOutlineSmallButton, 0, 0)

            runCatching {
                binding.root.cardElevation = 0f
                text = attr.getString(R.styleable.LgnWhiteOutlineSmallButton_android_text).orEmpty()
                textSize = attr.getString(R.styleable.LgnWhiteOutlineSmallButton_textSize).orEmpty()
                isLoading = attr.getBoolean(R.styleable.LgnWhiteOutlineSmallButton_isLoading, false)
                startIconDrawable =
                    attr.getDrawable(R.styleable.LgnWhiteOutlineSmallButton_startIconDrawable)
                endIconDrawable =
                    attr.getDrawable(R.styleable.LgnWhiteOutlineSmallButton_endIconDrawable)
                colorSet =
                    parseColorSet(attr.getInt(R.styleable.LgnWhiteOutlineSmallButton_colorSet, 0))
                isEnable =
                    attr.getBoolean(R.styleable.LgnWhiteOutlineSmallButton_android_enabled, true)
            }.onSuccess { attr.recycle() }
        }
    }
}
