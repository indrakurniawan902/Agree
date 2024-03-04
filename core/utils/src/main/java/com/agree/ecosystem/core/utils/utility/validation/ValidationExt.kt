package com.agree.ecosystem.core.utils.utility.validation

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.agree.ecosystem.core.utils.domain.reqres.model.ValidationModel
import com.agree.ecosystem.core.utils.utility.validation.rules.AgrCheckableRule
import com.agree.ecosystem.core.utils.utility.validation.rules.ChipRule
import com.devbase.presentation.validation.rules.BaseRule
import com.devbase.presentation.validation.rules.CheckedRule
import com.devbase.presentation.validation.rules.RegexRule
import com.devbase.presentation.viewbinding.DevActivity
import com.devbase.presentation.viewbinding.DevFragment
import com.telkom.legion.component.checkbox.base.LgnCheckBox
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.checkbox.base.LgnCheckBoxGroup
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.chips.base.LgnChipGroup
import com.telkom.legion.component.radio.base.LgnRadioButton
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.radio.base.LgnRadioGroup
import com.telkom.legion.component.textfield.*
import com.telkom.legion.component.textfield.base.LgnTextField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * Validation Frameworks with Kotlin Extension Function, is hoped that developers can easily
 * perform form validation and reduce boilerplate and repetitive actions,
 * No more nested ifs that are like mountains,
 * and no more nested whens that make it hard to sleep
 * @author @telkomdev-abdul
 * @since 2 March 2022
 * @see "https://agree-ecosystem-dev.web.app/guideline/form-validation/"
 */

/**
 * Initialization MutableStateFlow using kotlin delegate
 * @author @telkomdev-abdul
 * @since 2 March 2022
 */
inline fun <reified T : ViewBinding> DevActivity<T>.validation(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    defaultValue: Boolean = false,
    defaultMessage: String = "",
    crossinline view: T.() -> View
): Lazy<MutableStateFlow<ValidationModel>> {
    return lazy(mode) {
        MutableStateFlow(ValidationModel(view.invoke(binding), defaultValue, defaultMessage))
    }
}

/**
 * Initialization MutableStateFlow using kotlin delegate
 * @author @telkomdev-abdul
 * @since 2 March 2022
 */
inline fun <reified T : ViewBinding> DevFragment<T>.validation(
    mode: LazyThreadSafetyMode = LazyThreadSafetyMode.SYNCHRONIZED,
    defaultValue: Boolean = false,
    defaultMessage: String = "",
    crossinline view: T.() -> View
): Lazy<MutableStateFlow<ValidationModel>> {
    return lazy(mode) {
        MutableStateFlow(ValidationModel(view.invoke(binding), defaultValue, defaultMessage))
    }
}

/**
 * Initialization MutableStateFlow from views
 * @author @telkomdev-abdul
 * @since 12 April 2022
 */
fun View.initState(
    defaultValue: Boolean = false,
    defaultMessage: String = ""
): MutableStateFlow<ValidationModel> {
    return MutableStateFlow(ValidationModel(this, defaultValue, defaultMessage))
}

/**
 * Update State from MutableStateFlow
 * @author @telkomdev-abdul
 * @since 2 March 2022
 */
fun MutableStateFlow<ValidationModel>.update(
    view: View,
    isValid: Boolean,
    errorMessage: String
) {
    view.findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.Main.immediate) {
        emit(ValidationModel(view, isValid, errorMessage))
    }
}

/**
 * Add Compatibility AgrTextField with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 2 March 2022
 */
fun LgnTextField.addRule(
    state: MutableStateFlow<ValidationModel>?,
    vararg rule: BaseRule
) {
    val root = when (this) {
        is LgnCalendarField -> this
        is LgnPasswordField -> this
        is LgnDropdownField -> this
        is LgnSingleUnitField -> this
        is LgnTextAreaField -> this
        is LgnTimeField -> this
        else -> this as LgnSingleField
    }
    state?.update(root, false, "")
    val rules = rule.toList()
    root.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do Nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do Nothing
            }

            override fun afterTextChanged(s: Editable?) {
                val result = rules.firstOrNull { !it.validate(editText!!) }
                state?.update(root, result == null, result?.errorMessage ?: "")
            }
        }
        root.editText?.removeTextChangedListener(textWatcher)
        root.editText?.addTextChangedListener(textWatcher)
        if (root.text.isNotEmpty()) {
            val oldText = root.text
            root.text = oldText
        }
    }
}

/**
 * Add Compatibility CheckBox with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 2 March 2022
 */
fun CheckBox.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setOnCheckedChangeListener { _, _ ->
            if (rule is CheckedRule) {
                val result = rule.validate(this@addRule)
                state?.update(this@addRule, result, rule.errorMessage)
            } else {
                throw Exception("That Rule can not implemented with CheckBox.")
            }
        }
    }
}

/**
 * Add Compatibility AgrCheckBox with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
fun LgnCheckBox.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setOnCheckedChangeListener { _, _ ->
            when (rule) {
                is CheckedRule -> {
                    val result = rule.validate(this@addRule as CheckBox)
                    state?.update(this@addRule, result, rule.errorMessage)
                }
                is AgrCheckableRule -> {
                    val result = rule.validate(this@addRule)
                    state?.update(this@addRule, result, rule.errorMessage)
                }
                else -> {
                    throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
                }
            }
        }
    }
}

/**
 * Add Compatibility AgrCheckBoxGroup with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
fun LgnCheckBoxGroup.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setOnCheckedChangeListener {
            if (rule is AgrCheckableRule) {
                val result = rule.validate(this@addRule)
                state?.update(this@addRule, result, rule.errorMessage)
            } else {
                throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
            }
        }
    }
}

/**
 * Add Compatibility AgrCheckBoxContainer with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
fun LgnCheckBoxContainer.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    var isStartingUp = true
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setOnCheckedChangeListener {
            if (rule is AgrCheckableRule) {
                val result = rule.validate(this@addRule)
                if (!isStartingUp) {
                    state?.update(this@addRule, result, rule.errorMessage)
                } else {
                    isStartingUp = false
                }
            } else {
                throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
            }
        }
    }
}

/**
 * Add Compatibility RadioGroup with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 02 March 2022
 */
fun RadioGroup.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setOnCheckedChangeListener { _, _ ->
            if (rule is CheckedRule) {
                val result = rule.validate(this@addRule)
                state?.update(this@addRule, result, rule.errorMessage)
            } else {
                throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
            }
        }
    }
}

/**
 * Add Compatibility AgrRadioButton with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
fun LgnRadioButton.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setOnCheckedChangeListener { _, _ ->
            when (rule) {
                is CheckedRule -> {
                    val result = rule.validate(this@addRule as RadioButton)
                    state?.update(this@addRule, result, rule.errorMessage)
                }
                is AgrCheckableRule -> {
                    val result = rule.validate(this@addRule)
                    state?.update(this@addRule, result, rule.errorMessage)
                }
                else -> {
                    throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
                }
            }
        }
    }
}

/**
 * Add Compatibility AgrRadioGroup with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
fun LgnRadioGroup.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setListener {
            if (rule is AgrCheckableRule) {
                val result = rule.validate(this@addRule)
                state?.update(this@addRule, result, rule.errorMessage)
            } else {
                throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
            }
        }
    }
}

/**
 * Add Compatibility AgrRadioContainer with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 14 July 2022
 */
fun LgnRadioContainer.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setListener {
            if (rule is AgrCheckableRule) {
                val result = rule.validate(this@addRule)
                state?.update(this@addRule, result, rule.errorMessage)
            } else {
                throw Exception("That Rule can not implemented with ${this@addRule::class.java.simpleName}.")
            }
        }
    }
}

/**
 * Add Compatibility AgrChipContainer with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 28 April 2022
 */
fun LgnChipContainer.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setListener { value ->
            when (rule) {
                is RegexRule -> {
                    state?.update(this@addRule, value.isNotEmpty(), rule.errorMessage)
                }
                is AgrCheckableRule -> {
                    val result = rule.validate(this@addRule)
                    state?.update(this@addRule, result, rule.errorMessage)
                }
                else -> {
                    throw Exception("That Rule can not implemented with Chip Container.")
                }
            }
        }
    }
}

/**
 * Add Compatibility AgrChipGroup with Validation Frameworks
 * @author @telkomdev-abdul
 * @since 28 April 2022
 */
fun LgnChipGroup.addRule(
    state: MutableStateFlow<ValidationModel>?,
    rule: BaseRule,
    defaultValue: Boolean = false
) {
    state?.update(this, defaultValue, "")
    this.findViewTreeLifecycleOwner()?.lifecycleScope?.launchWhenResumed {
        this@addRule.setListener { _ ->
            when (rule) {
                is ChipRule -> {
                    state?.update(this@addRule, rule.validate(this@addRule), rule.errorMessage)
                }
                is AgrCheckableRule -> {
                    val result = rule.validate(this@addRule)
                    state?.update(this@addRule, result, rule.errorMessage)
                }
                else -> {
                    throw Exception("That Rule can not implemented with Chip Group.")
                }
            }
        }
    }
}
