package com.agree.ui.builder

import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.agree.ui.builder.widget.AgrFormContainer
import com.agree.ui.domain.model.DynamicFormSchema
import com.agree.ui.utils.enums.FormInputType
import com.agree.ui.utils.enums.FormType
import com.telkom.legion.component.image.MultiplePhotoField
import kotlinx.coroutines.launch

/**
 * Dynamic Form Builder with or without Scheme
 */
class AgrDynamicForm(
    private val container: AgrFormContainer
) {

    private val formSchema: MutableList<DynamicFormSchema> = mutableListOf()

    private val formViews: MutableList<View> = mutableListOf()

    private var fragmentManager: FragmentManager? = null

    private val builder: Builder by lazy {
        Builder(container.context)
    }

    /**
     * Add and Replace Current Schema List
     */
    fun addAndReplaceFormSchema(forms: List<DynamicFormSchema>): AgrDynamicForm {
        formSchema.clear()
        formViews.clear()
        formSchema.addAll(forms)
        return this
    }

    /**
     * Add or Update New Schema List into Current Schema List
     */
    fun addOrUpdateSchema(forms: List<DynamicFormSchema>): AgrDynamicForm {

        // Prepare Comparator
        val comparator: DiffUtil.Callback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return formSchema.size
            }

            override fun getNewListSize(): Int {
                return forms.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return formSchema[oldItemPosition] == forms[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return formSchema[oldItemPosition].id == forms[newItemPosition].id
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return super.getChangePayload(oldItemPosition, newItemPosition)
            }
        }

        // Do a Update
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(comparator)
        diffResult.dispatchUpdatesTo(object : ListUpdateCallback {
            override fun onInserted(position: Int, count: Int) {
                formSchema.add(position, forms[position])
            }

            override fun onRemoved(position: Int, count: Int) {
                val removedList = formSchema.subList(position, position + count)
                formSchema.removeAll(removedList)
            }

            override fun onMoved(fromPosition: Int, toPosition: Int) {
                formSchema.removeAt(fromPosition)
                formSchema.add(toPosition, forms[toPosition])
            }

            override fun onChanged(position: Int, count: Int, payload: Any?) {
                if (payload is DynamicFormSchema) {
                    formSchema[position] = payload
                } else {
                    formSchema[position] = forms[position]
                }
            }
        })
        formViews.clear()
        return this
    }

    /**
     * Override Default Fragment Manager
     */
    fun setFragmentmanager(fm: FragmentManager?): AgrDynamicForm {
        fragmentManager = fm
        return this
    }

    /**
     * Parsing Schema and convert into view's
     */
    fun parseSchema(): AgrDynamicForm {
        container.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            formSchema.forEach { schema ->
                formViews.add(
                    when (schema.componentType) {
                        FormType.FORM_COMP_TYPE_DATE -> builder.setupCalendarField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            fragmentManager = this@AgrDynamicForm.fragmentManager
                                ?: (container.context as FragmentActivity).supportFragmentManager
                            inputType = FormInputType.parseInputType(schema.fieldType)
                            placeHolder = schema.placeHolder
                            isEnable = schema.isEnable
                            setOnClickListener { }
                        }
                        FormType.FORM_COMP_TYPE_TEXT -> builder.setupSingleTextField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            prefixText = schema.prefix
                            suffixText = schema.suffix
                            inputType = FormInputType.parseInputType(schema.fieldType)
                            placeHolder = schema.placeHolder
                            isEnable = schema.isEnable
                        }
                        FormType.FORM_COMP_TYPE_DROPDOWN -> builder.setupDropdownField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            placeHolder = schema.placeHolder
                            fragmentManager = this@AgrDynamicForm.fragmentManager
                                ?: (container.context as FragmentActivity).supportFragmentManager
                            addAll(schema.options)
                            isEnable = schema.isEnable
                            setOnClickListener { }
                        }
                        FormType.FORM_COMP_TYPE_TEXT_AREA -> builder.setupTextAreaField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            placeHolder = schema.placeHolder
                            isEnable = schema.isEnable
                        }
                        FormType.FORM_COMP_TYPE_PASSWORD -> builder.setupPasswordField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            placeHolder = schema.placeHolder
                            isEnable = schema.isEnable
                            inputType = FormInputType.parseInputType(schema.fieldType)
                        }
                        FormType.FORM_COMP_TYPE_UNIT -> builder.setupSingleUnitField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            placeHolder = schema.placeHolder
                            inputType = FormInputType.parseInputType(schema.fieldType)
                            fragmentManager = this@AgrDynamicForm.fragmentManager
                                ?: (container.context as FragmentActivity).supportFragmentManager
                            addAll(schema.options)
                            isEnable = schema.isEnable
                        }
                        FormType.FORM_COMP_TYPE_TIME -> builder.setupTimeField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            text = schema.value
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            placeHolder = schema.placeHolder
                            fragmentManager =
                                (container.context as FragmentActivity).supportFragmentManager
                            isEnable = schema.isEnable
                            setOnClickListener { }
                        }
                        FormType.FORM_COMP_TYPE_RADIO -> builder.setupRadioField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            addAll(schema.options)
                            isEnabled = schema.isEnable
                        }
                        FormType.FORM_COMP_TYPE_PHOTO_UPLOAD -> builder.setupMultiplePhotoField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            helper = schema.helper
                            limit = if (schema.max < 1) {
                                1
                            } else {
                                schema.max
                            }
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            addImageLoadedListener { imageLoaded, totalImages ->
                                if (imageLoaded == totalImages) {
                                    isEnable = schema.isEnable
                                }
                            }
                            addAll(schema.value.split(","))
                        }
                        FormType.FORM_COMP_TYPE_CHECKBOX -> builder.setupCheckBoxField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            addAll(schema.options)
                            isEnabled = schema.isEnable
                        }
                        FormType.FORM_COMP_TYPE_CHIPS -> builder.setupChipField {
                            id = View.generateViewId()
                            tag = schema.id
                            hint = schema.label
                            helper = schema.helper
                            if (schema.required)
                                isRequired = schema.required
                            else
                                isOptional = true
                            addAll(schema.options)
                            isEnabled = schema.isEnable
                        }
                    }
                )
            }
        }
        return this
    }

    /**
     * Build View's into Container
     */
    fun build(): AgrFormContainer {
        return container.apply {
            addAndReplaceAll(
                formViews.map {
                    it to LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        if (it is MultiplePhotoField) {
                            val marginVertical = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                16f, context.resources.displayMetrics
                            )
                            setMargins(
                                marginStart,
                                marginVertical.toInt(),
                                marginEnd,
                                marginBottom
                            )
                        }
                    }
                }
            )
        }
    }

    /**
     * Update View's in Container with new Schema
     */
    fun update(): AgrFormContainer {
        return container.apply {
            addOrUpdateAll(
                formViews.map {
                    it to LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        if (it is MultiplePhotoField) {
                            val marginVertical = TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                16f, context.resources.displayMetrics
                            )
                            setMargins(
                                marginStart,
                                marginVertical.toInt(),
                                marginEnd,
                                marginBottom
                            )
                        }
                    }
                }
            )
        }
    }

    companion object {
        fun schema(container: AgrFormContainer, block: Schema.() -> Unit): Schema {
            return Schema(container).apply(block)
        }
    }
}
