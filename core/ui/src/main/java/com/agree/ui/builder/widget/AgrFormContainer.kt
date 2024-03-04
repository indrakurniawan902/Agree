package com.agree.ui.builder.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.agree.ui.builder.AgrDynamicForm
import com.agree.ui.builder.Schema
import com.agree.ui.databinding.LayoutFormContainerBinding
import com.oratakashi.viewbinding.core.binding.customview.viewBinding
import com.telkom.legion.component.checkbox.base.LgnCheckBoxContainer
import com.telkom.legion.component.chips.base.LgnChipContainer
import com.telkom.legion.component.image.MultiplePhotoField
import com.telkom.legion.component.radio.base.LgnRadioContainer
import com.telkom.legion.component.textfield.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * View Container for Dynamic Layout
 * @author @telkomdev-abdul
 * @since 7 July 2022
 */
class AgrFormContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ListUpdateCallback {

    private val binding: LayoutFormContainerBinding by viewBinding()

    private var schemaInstance: Schema? = null

    private val formsViews: MutableList<Pair<View, ViewGroup.LayoutParams>> = ArrayList()

    private val diffCallback: DiffUtil.Callback by lazy {
        object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return getChildAsTags().size
            }

            override fun getNewListSize(): Int {
                return formsViews.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return getChildAsTags()[oldItemPosition] == formsViews[newItemPosition].first.tag.toString()
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return getChildAsTags()[oldItemPosition] == formsViews[newItemPosition].first.tag.toString()
            }
        }
    }

    /**
     * Setup Dynamic Form Schema
     */
    fun schema(
        block: Schema.() -> Unit
    ): Schema {
        if (schemaInstance == null) {
            schemaInstance = AgrDynamicForm.schema(this, block)
        }
        return schemaInstance?.apply(block) ?: AgrDynamicForm.schema(this, block)
    }

    /**
     * Get View's in Container as List
     */
    fun getChildAsList(): List<View> {
        return binding.containerDynamic.children.toList()
    }

    /**
     * Get View's Tags in Container as List
     */
    fun getChildAsTags(): List<String> {
        return getChildAsList().map { it.tag.toString() }
    }

    /**
     * Get All Dropdown field View's
     */
    fun getAllDropDownField(): List<LgnDropdownField> {
        return getChildAsList().filterIsInstance(LgnDropdownField::class.java)
    }

    /**
     * Get Dropdown field by Tags
     */
    fun getDropDownFieldByTags(
        tags: String,
        block: LgnDropdownField?.() -> Unit = {}
    ): LgnDropdownField? {
        return getAllDropDownField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Calendar Field View's
     */
    fun getAllCalendarField(): List<LgnCalendarField> {
        return getChildAsList().filterIsInstance(LgnCalendarField::class.java)
    }

    /**
     * Get Calendar field by Tags
     */
    fun getCalendarFieldByTags(
        tags: String,
        block: LgnCalendarField?.() -> Unit = {}
    ): LgnCalendarField? {
        return getAllCalendarField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Single Field View's
     */
    fun getAllSingleField(): List<LgnSingleField> {
        return getChildAsList().filterIsInstance(LgnSingleField::class.java)
    }

    /**
     * Get Single field by Tags
     */
    fun getSingleFieldByTags(
        tags: String,
        block: LgnSingleField?.() -> Unit = {}
    ): LgnSingleField? {
        return getAllSingleField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Password Field View's
     */
    fun getAllPasswordField(): List<LgnPasswordField> {
        return getChildAsList().filterIsInstance(LgnPasswordField::class.java)
    }

    /**
     * Get Password field by Tags
     */
    fun getPasswordFieldByTags(
        tags: String,
        block: LgnPasswordField?.() -> Unit = {}
    ): LgnPasswordField? {
        return getAllPasswordField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Text Area Field View's
     */
    fun getAllTextAreaField(): List<LgnTextAreaField> {
        return getChildAsList().filterIsInstance(LgnTextAreaField::class.java)
    }

    /**
     * Get Text Area field by Tags
     */
    fun getTextAreaFieldByTags(
        tags: String,
        block: LgnTextAreaField?.() -> Unit = {}
    ): LgnTextAreaField? {
        return getAllTextAreaField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Single Unit Field View's
     */
    fun getAllSingleUnitField(): List<LgnSingleUnitField> {
        return getChildAsList().filterIsInstance(LgnSingleUnitField::class.java)
    }

    /**
     * Get Single Unit field by Tags
     */
    fun getSingleUnitFieldByTags(
        tags: String,
        block: LgnSingleUnitField?.() -> Unit = {}
    ): LgnSingleUnitField? {
        return getAllSingleUnitField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Time Field View's
     */
    fun getAllTimeField(): List<LgnTimeField> {
        return getChildAsList().filterIsInstance(LgnTimeField::class.java)
    }

    /**
     * Get Time field by Tags
     */
    fun getTimeFieldByTags(tags: String, block: LgnTimeField?.() -> Unit = {}): LgnTimeField? {
        return getAllTimeField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Radio Field View's
     */
    fun getAllRadioField(): List<LgnRadioContainer> {
        return getChildAsList().filterIsInstance(LgnRadioContainer::class.java)
    }

    /**
     * Get Radio field by Tags
     */
    fun getRadioFieldByTags(
        tags: String,
        block: LgnRadioContainer?.() -> Unit = {}
    ): LgnRadioContainer? {
        return getAllRadioField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All Multiple Photo Field View's
     */
    fun getAllMultiplePhotoField(): List<MultiplePhotoField> {
        return getChildAsList().filterIsInstance(MultiplePhotoField::class.java)
    }

    /**
     * Get Multiple Photo field by Tags
     */
    fun getMultiplePhotoFieldByTags(
        tags: String,
        block: MultiplePhotoField?.() -> Unit = {}
    ): MultiplePhotoField? {
        return getAllMultiplePhotoField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All CheckBox Field View's
     */
    fun getAllCheckBoxField(): List<LgnCheckBoxContainer> {
        return getChildAsList().filterIsInstance(LgnCheckBoxContainer::class.java)
    }

    /**
     * Get CheckBox field by Tags
     */
    fun getCheckBoxFieldByTags(
        tags: String,
        block: LgnCheckBoxContainer?.() -> Unit = {}
    ): LgnCheckBoxContainer? {
        return getAllCheckBoxField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Get All CheckBox Field View's
     */
    fun getAllChipsField(): List<LgnChipContainer> {
        return getChildAsList().filterIsInstance(LgnChipContainer::class.java)
    }

    /**
     * Get Chips field by Tags
     */
    fun getChipsFieldByTags(
        tags: String,
        block: LgnChipContainer?.() -> Unit = {}
    ): LgnChipContainer? {
        return getAllChipsField().firstOrNull { it.tag == tags }.apply(block)
    }

    /**
     * Method to Add And Replace All View's
     */
    fun addAndReplaceAll(views: List<Pair<View, ViewGroup.LayoutParams>>) {
        with(binding) {
            formsViews.clear()
            formsViews.addAll(views)
            containerDynamic.removeAllViewsInLayout()
            findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                views.forEach {
                    containerDynamic.addView(it.first, it.second)
                }
            }
        }
    }

    /**
     * Method to Add And Update All View's
     */
    fun addOrUpdateAll(views: List<Pair<View, ViewGroup.LayoutParams>>) {
        with(binding) {
            findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.Main.immediate) {

                // Remove old list view and replace it
                formsViews.clear()
                formsViews.addAll(views)

                // Compare updated list with existing views and update if different
                val result = DiffUtil.calculateDiff(diffCallback)
                result.dispatchUpdatesTo(this@AgrFormContainer)
            }
        }
    }

    /**
     * Method on DiffUtils Detect Item Inserted
     */
    override fun onInserted(position: Int, count: Int) {
        with(binding) {
            findViewTreeLifecycleOwner()?.lifecycleScope?.launch(Dispatchers.Main.immediate) {
                runCatching {
                    for (i in position until (position + count)) {
                        val view = formsViews[i].first
                        if (view.parent != null) {
                            view.removeSelf()
                        }
                        containerDynamic.addView(
                            view,
                            i,
                            formsViews[i].second
                        )
                    }
                }
            }
        }
    }

    /**
     * Method on DiffUtils Detect Item Removed
     */
    override fun onRemoved(position: Int, count: Int) {
        with(binding) {
            containerDynamic.removeViews(position, count)
        }
    }

    /**
     * Method on DiffUtils Detect Item Moved
     */
    override fun onMoved(fromPosition: Int, toPosition: Int) {
        with(binding) {
            containerDynamic.removeViewAt(fromPosition)
            containerDynamic.addView(
                formsViews[toPosition].first,
                toPosition,
                formsViews[toPosition].second
            )
        }
    }

    /**
     * Method on DiffUtils Detect Item Changed
     */
    @Suppress("UNCHECKED_CAST")
    override fun onChanged(position: Int, count: Int, payload: Any?) {
        with(binding) {
            if (payload is Pair<*, *>) {
                runCatching {
                    val data = (payload as Pair<View, ViewGroup.LayoutParams>)
                    containerDynamic.addView(
                        data.first,
                        position,
                        data.second
                    )
                    containerDynamic.removeViewAt(position + 1)
                }.onFailure {
                    val data = formsViews[position]
                    containerDynamic.addView(
                        data.first,
                        position,
                        data.second
                    )
                    containerDynamic.removeViewAt(position + 1)
                }
            } else {
                val data = formsViews[position]
                containerDynamic.addView(
                    data.first,
                    position,
                    data.second
                )
                containerDynamic.removeViewAt(position + 1)
            }
        }
    }

    /**
     * Detach Views from parent
     */
    private fun View?.removeSelf() {
        this ?: return
        val parentView = parent as? ViewGroup ?: return
        parentView.removeView(this)
    }
}
