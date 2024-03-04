package com.agree.locales.presentation.transformer

import com.agree.locales.presentation.transformer.TextFieldTransformer.transform
import com.telkom.legion.component.textfield.LgnSingleField
import dev.b3nedikt.reword.transformer.ViewTransformer
import dev.b3nedikt.reword.util.updateTexts

/**
 * [LgnSingleField] Support for Locale Framework
 */
object SingleFieldTransformer : ViewTransformer<LgnSingleField> {

    private const val ATTRIBUTE_HINT = "hint"
    private const val ATTRIBUTE_ANDROID_HINT = "android:hint"
    private const val ATTRIBUTE_PLACEHOLDER = "placeholderText"
    private const val ATTRIBUTE_APP_PLACEHOLDER = "app:placeholderText"
    private const val ATTRIBUTE_HELPER = "helperText"
    private const val ATTRIBUTE_APP_HELPER = "app:helperText"
    private const val ATTRIBUTE_PREFIX = "prefixText"
    private const val ATTRIBUTE_APP_PREFIX = "app:prefixText"
    private const val ATTRIBUTE_SUFFIX = "suffixText"
    private const val ATTRIBUTE_APP_SUFFIX = "app:suffixText"

    override val supportedAttributes: Set<String> = setOf(
        ATTRIBUTE_HINT,
        ATTRIBUTE_ANDROID_HINT,
        ATTRIBUTE_PLACEHOLDER,
        ATTRIBUTE_APP_PLACEHOLDER,
        ATTRIBUTE_HELPER,
        ATTRIBUTE_APP_HELPER,
        ATTRIBUTE_PREFIX,
        ATTRIBUTE_APP_PREFIX,
        ATTRIBUTE_SUFFIX,
        ATTRIBUTE_APP_SUFFIX
    )

    override val viewType: Class<in LgnSingleField> = LgnSingleField::class.java

    override fun LgnSingleField.transform(attrs: Map<String, Int>) {
        attrs.forEach { entry ->
            when (entry.key) {
                ATTRIBUTE_HINT, ATTRIBUTE_ANDROID_HINT           -> updateTexts(entry.value) {
                    this.hint = it.toString()
                    val isRequired = this.isRequired
                    val isOptional = this.isOptional
                    this.isRequired = isRequired
                    this.isOptional = isOptional
                }
                ATTRIBUTE_PLACEHOLDER, ATTRIBUTE_APP_PLACEHOLDER -> updateTexts(entry.value) {
                    this.placeHolder = it.toString()
                }
                ATTRIBUTE_HELPER, ATTRIBUTE_APP_HELPER           -> updateTexts(entry.value) {
                    this.helper = it.toString()
                }
                ATTRIBUTE_PREFIX, ATTRIBUTE_APP_PREFIX           -> updateTexts(entry.value) {
                    this.prefixText = it.toString()
                }
                ATTRIBUTE_SUFFIX, ATTRIBUTE_APP_SUFFIX           -> updateTexts(entry.value) {
                    this.suffixText = it.toString()
                }
            }
        }
    }
}