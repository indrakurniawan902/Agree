package com.agree.locales.presentation.transformer

import com.telkom.legion.component.button.base.LgnButton
import dev.b3nedikt.reword.transformer.ViewTransformer
import dev.b3nedikt.reword.util.updateTexts

/**
 * [LgnButton] Support for Locale Framework
 */
internal object ButtonViewTransformer : ViewTransformer<LgnButton> {

    private const val ATTRIBUTE_ANDROID_TEXT = "android:text"
    private const val ATTRIBUTE_TEXT = "text"

    override val supportedAttributes: Set<String> = setOf(
        ATTRIBUTE_TEXT,
        ATTRIBUTE_ANDROID_TEXT
    )
    override val viewType: Class<in LgnButton> = LgnButton::class.java

    override fun LgnButton.transform(attrs: Map<String, Int>) {
        attrs.forEach { entry ->
            when (entry.key) {
                ATTRIBUTE_TEXT, ATTRIBUTE_ANDROID_TEXT -> updateTexts(entry.value) {
                    this.text = it.toString()
                }
            }
        }
    }
}