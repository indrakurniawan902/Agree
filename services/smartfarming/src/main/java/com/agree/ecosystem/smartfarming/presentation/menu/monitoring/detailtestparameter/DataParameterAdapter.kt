package com.agree.ecosystem.smartfarming.presentation.menu.monitoring.detailtestparameter

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.smartfarming.R
import com.agree.ecosystem.smartfarming.databinding.ItemStatusParameterBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ReferenceItem
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding
import com.telkom.legion.component.utility.extension.requiredColor
import com.agree.ui.R as R_AGR

class DataParameterAdapter(
) : DevRecyclerViewAdapter<ReferenceItem>() {

    inner class ViewHolder(private val bindLayout: ItemStatusParameterBinding) :
        DevItemViewHolder<ReferenceItem>(bindLayout) {
        @SuppressLint("ResourceAsColor", "SetTextI18n")
        override fun bind(data: ReferenceItem?) {
            with(bindLayout) {
                data?.let { reference ->
                    when (reference.status) {
                        ReferenceItem.Status.GOOD -> {
                            tvStatus.apply {
                                text = getStringResource(R.string.good)
                                setTextColor(UiKitAttrs.colorPrimary500.getAttrsValue(root.context))
                            }
                            cvParameter.apply {
                                strokeColor = root.context.requiredColor(UiKitAttrs.colorPrimary500)
                                setBackgroundColor(root.context.requiredColor(UiKitAttrs.colorPrimary25))
                            }
                            ivStatus.setImageResource(R.drawable.smile)
                        }

                        ReferenceItem.Status.WARY -> {
                            tvStatus.apply {
                                text = getStringResource(R.string.wary)
                                setTextColor(UiKitAttrs.warning_500.getAttrsValue(root.context))
                            }
                            cvParameter.apply {
                                strokeColor = root.context.requiredColor(UiKitAttrs.warning_500)
                                setBackgroundColor(root.context.requiredColor(UiKitAttrs.warning_25))
                            }
                            ivStatus.setImageResource(R.drawable.alert_triangle)
                        }

                        ReferenceItem.Status.BAD -> {
                            tvStatus.apply {
                                text = getStringResource(R.string.bad)
                                setTextColor(UiKitAttrs.error_500.getAttrsValue(root.context))
                            }
                            cvParameter.apply {
                                strokeColor = root.context.requiredColor(UiKitAttrs.error_500)
                                setBackgroundColor(root.context.requiredColor(UiKitAttrs.error_25))
                            }
                            ivStatus.setImageResource(R.drawable.frown)
                        }
                    }
                    val dataListParam =
                        reference.parameter?.joinToString("\n") { params ->
                            val type = when (params.type) {
                                "lower-than" -> "<"
                                "greater-than" -> ">"
                                else -> ""
                            }
                            "$type${params.value}`${params.suffix}"
                        }
                            .orEmpty()
                            .replace(Regex("[\\[\\],]"), "")
                            .replace(" ", "\n")
                            .replace("`", " ")

                    tvListParam.apply {
                        setLineSpacing(
                            TypedValue.applyDimension(
                                TypedValue.COMPLEX_UNIT_DIP,
                                8.0f,
                                root.context.resources.displayMetrics
                            ), 1.0f
                        )
                        text = dataListParam
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<ReferenceItem> {
        return ViewHolder(inflateBinding(parent))
    }
}