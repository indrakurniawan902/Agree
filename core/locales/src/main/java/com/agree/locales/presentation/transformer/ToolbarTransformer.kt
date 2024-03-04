package com.agree.locales.presentation.transformer

import com.telkom.legion.component.toolbar.LgnToolbarWhite
import dev.b3nedikt.reword.transformer.ViewTransformer
import dev.b3nedikt.reword.util.updateTexts

/**
 * [LgnToolbarWhite] Support for Locale Framework
 */
object ToolbarTransformer: ViewTransformer<LgnToolbarWhite> {

    private const val ATTRIBUTE_ANDROID_TEXT = "android:text"
    private const val ATTRIBUTE_TEXT = "text"
    private const val ATTRIBUTE_TITLE = "title"
    private const val ATTRIBUTE_APP_TITLE = "app:title"

    override val supportedAttributes: Set<String> = setOf(
        ATTRIBUTE_TEXT,
        ATTRIBUTE_ANDROID_TEXT,
        ATTRIBUTE_APP_TITLE,
        ATTRIBUTE_TITLE
    )

    override val viewType: Class<in LgnToolbarWhite> = LgnToolbarWhite::class.java

    override fun LgnToolbarWhite.transform(attrs: Map<String, Int>) {
        attrs.forEach { entry ->
            when(entry.key) {
                ATTRIBUTE_TEXT, ATTRIBUTE_ANDROID_TEXT -> updateTexts(entry.value) {
                    this.text = it.toString()
                }
                ATTRIBUTE_APP_TITLE, ATTRIBUTE_TITLE -> updateTexts(entry.value) {
                    this.title = it
                }
            }
        }
    }
}