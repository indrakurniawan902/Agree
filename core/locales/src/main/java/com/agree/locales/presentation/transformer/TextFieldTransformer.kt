package com.agree.locales.presentation.transformer

import com.telkom.legion.component.textfield.base.LgnTextField
import dev.b3nedikt.reword.transformer.ViewTransformer
import dev.b3nedikt.reword.util.updateTexts

/**
 * [LgnTextField] Support for Locale Framework
 */
internal object TextFieldTransformer : ViewTransformer<LgnTextField>{

    private const val ATTRIBUTE_HINT = "hint"
    private const val ATTRIBUTE_ANDROID_HINT = "android:hint"
    private const val ATTRIBUTE_PLACEHOLDER = "placeholderText"
    private const val ATTRIBUTE_APP_PLACEHOLDER = "app:placeholderText"
    private const val ATTRIBUTE_HELPER = "helperText"
    private const val ATTRIBUTE_APP_HELPER = "app:helperText"

    override val supportedAttributes: Set<String> = setOf(
        ATTRIBUTE_HINT,
        ATTRIBUTE_ANDROID_HINT,
        ATTRIBUTE_PLACEHOLDER,
        ATTRIBUTE_APP_PLACEHOLDER,
        ATTRIBUTE_HELPER,
        ATTRIBUTE_APP_HELPER
    )

    override val viewType: Class<in LgnTextField> = LgnTextField::class.java

    override fun LgnTextField.transform(attrs: Map<String, Int>) {
        attrs.forEach { entry ->
            when(entry.key) {
                ATTRIBUTE_HINT, ATTRIBUTE_ANDROID_HINT -> updateTexts(entry.value) {
                    this.hint = it.toString()
                    val isRequired = this.isRequired
                    val isOptional = this.isOptional
                    this.isRequired = isRequired
                    this.isOptional = isOptional
                }
                ATTRIBUTE_PLACEHOLDER, ATTRIBUTE_APP_PLACEHOLDER -> updateTexts(entry.value) {
                    this.placeHolder = it.toString()
                }
                ATTRIBUTE_HELPER, ATTRIBUTE_APP_HELPER -> updateTexts(entry.value) {
                    this.helper = it.toString()
                }
            }
        }
    }
}