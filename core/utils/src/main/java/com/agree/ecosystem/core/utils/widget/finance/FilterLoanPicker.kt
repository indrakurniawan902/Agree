package com.agree.ecosystem.core.utils.widget.finance

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.agree.ecosystem.core.utils.domain.reqres.model.FinanceCustomMapData
import com.agree.ui.UiKitDrawable
import com.telkom.legion.component.checkbox.LgnPrimaryCheckBoxContainer
import com.telkom.legion.component.utility.extension.updateState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class FilterLoanPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

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

    fun addAll(data: List<FinanceCustomMapData>) {
        removeAllViews()
        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            val filterData = data.groupBy { it.key }

            filterData.forEach { (key, value) ->
                val views = LgnPrimaryCheckBoxContainer(context).apply {
                    id = View.generateViewId()
                    hint = key
                    tag = key
                    showDividers = SHOW_DIVIDER_MIDDLE
                    addAll(value.map { it.value })
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

    fun setOnCheckedChangeListener(listener: (isCheckedAll: Boolean) -> Unit) {
        val stateList: MutableList<StateFlow<Boolean>> = ArrayList()

        findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
            val container = children.toList()
                .filterIsInstance(LgnPrimaryCheckBoxContainer::class.java)

            container.forEach {
                stateList.add(
                    MutableStateFlow(it.isCheckedAll).apply {
                        it.setOnCheckedChangeListener { _ ->
                            updateState(this, it.isCheckedAll)
                        }
                    }
                )
            }

            combine(stateList) { list ->
                val state = list.toList()
                    .filter { it }

                if (state.size == container.size) {
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
