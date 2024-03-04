package com.agree.ecosystem.utilities.presentation.widget.sectorpicker

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ui.UiKitDrawable
import com.telkom.legion.component.checkbox.LgnPrimaryCheckBoxContainer
import com.telkom.legion.component.utility.extension.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

/**
 * CustomView for List Sector in Sector BottomSheet Picker with Handle States for
 * Many CheckBox Container
 * @author @telkomdev-abdul
 * @since 24 July 2022
 */
class AgrSectorPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    /**
     * Property to set state checked all checkbox container, and get
     * state checked all in all checkbox container
     */
    var isCheckedAll: Boolean
        get() {
            return children.toList()
                .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)
                .filter { it.isCheckedAll }.size == children.toList()
                .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)
                .size
        }
        set(value) {
            children.toList()
                .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)
                .onEach { it.isCheckedAll = value }
        }

    /**
     * Property to set checked value and get checked value as String
     */
    var text: String
        get() {
            return children.toList()
                .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)
                .filter { it.text.isNotEmpty() }
                .joinToString(", ") { it.text }
        }
        set(value) {
            findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                val container = children.toList()
                    .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)
                value.split(", ").forEach { text ->
                    container.onEach {
                        it.text = text
                    }
                }
            }
        }

    /**
     * Helper for Add Container Programmatically
     */
    fun addAll(data: List<SubSector>) {
        removeAllViews()
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            val sector = data.groupBy { it.sectorName }
            sector.forEach { (sectorName, subSector) ->
                val views = LgnPrimaryCheckBoxContainer(context).apply {
                    id = View.generateViewId()
                    hint = sectorName
                    tag = sectorName
                    showDividers = SHOW_DIVIDER_MIDDLE
                    addAll(subSector.map { it.getFullSectorName() })
                }
                addView(
                    views,
                    LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT
                    )
                )
            }
        }
    }

    /**
     * Helper for Listening user interaction on child container
     */
    fun setOnCheckedChangeListener(listener: (isCheckedAll: Boolean) -> Unit) {
        val stateList: MutableList<StateFlow<Boolean>> = ArrayList()
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            val containers = children.toList()
                .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)

            containers.forEach {
                stateList.add(
                    MutableStateFlow(
                        it.isCheckedAll
                    ).apply {
                        it.setOnCheckedChangeListener { _ ->
                            updateState(this, it.isCheckedAll)
                        }
                    }
                )
            }

            combine(stateList) { list ->
                val state = list.toList()
                    .filter { it }

                if (state.size == containers.size) {
                    listener.invoke(true)
                } else {
                    listener.invoke(false)
                }
            }.collect()
        }
    }

    override fun setShowDividers(showDividers: Int) {
        super.setShowDividers(showDividers)
        if (showDividers != SHOW_DIVIDER_NONE) {
            dividerDrawable = ContextCompat.getDrawable(context, UiKitDrawable.separator_default)
        }
    }

    init {
        showDividers = SHOW_DIVIDER_MIDDLE
        orientation = VERTICAL
    }
}
