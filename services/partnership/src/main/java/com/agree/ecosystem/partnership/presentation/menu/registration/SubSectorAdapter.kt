package com.agree.ecosystem.partnership.presentation.menu.registration

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.ItemSubSectorPickerBinding
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ui.UiColors
import com.agree.ui.UiKitAttrs
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.tools.disable
import com.telkom.legion.component.utility.enums.ChipsType
import com.telkom.legion.component.utility.extension.requiredColor

class SubSectorAdapter(
    private val context: Context,
    private val onSubSectorAdapter: (subSector: SubSector) -> Unit,
) : DevRecyclerViewAdapter<SubSector>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DevItemViewHolder<SubSector> {
        return ViewHolder(
            ItemSubSectorPickerBinding.inflate(getLayoutInflater(parent), parent, false)
        )
    }

    inner class ViewHolder(private val bindLayout: ItemSubSectorPickerBinding) :
        DevItemViewHolder<SubSector>(bindLayout) {
        @SuppressLint("ResourceAsColor")
        override fun bind(data: SubSector?) {
            with(bindLayout) {
                btnAction.setOnClickListener {
                    onSubSectorAdapter(data!!)
                }
                data?.let { subSector ->
                    tvTitleSubSector.text = subSector.name
                    tvDescriptionSubSector.text =
                        context.getString(R.string.label_select_commodity_by_ector, subSector.name)
                    when (subSector.name.split(" ")[0]) {
                        "Peternakan" -> {
                            cardViewStyling(
                                ContextCompat.getColor(root.context, UiColors.agl_10),
                                ContextCompat.getColor(root.context, UiColors.agl_pressed),
                                ContextCompat.getColor(root.context, UiColors.agl_normal)
                            )
                        }
                        "Pertanian" -> {
                            cardViewStyling(
                                root.context.requiredColor(UiKitAttrs.colorPrimary100),
                                root.context.requiredColor(UiKitAttrs.colorPrimary700),
                                root.context.requiredColor(UiKitAttrs.colorPrimary)
                            )
                        }
                        "Perikanan" -> {
                            cardViewStyling(
                                ContextCompat.getColor(root.context, UiColors.aqf_10),
                                ContextCompat.getColor(root.context, UiColors.aqf_pressed),
                                ContextCompat.getColor(root.context, UiColors.aqf_normal)
                            )
                        }
                    }
                    if (subSector.status.toBooleanStrictOrNull() == false) {
                        cardViewStyling(
                            root.context.requiredColor(UiKitAttrs.colorTertiary_100),
                            root.context.requiredColor(UiKitAttrs.colorTertiary_50),
                            root.context.requiredColor(UiKitAttrs.colorTertiary_200)
                        )
                        btnAction.disable()
                        tvText.setTextColor(root.context.requiredColor(UiKitAttrs.colorTertiary_400))
                        tvDescriptionSubSector.setTextColor(root.context.requiredColor(UiKitAttrs.colorTertiary_400))
                        tvTitleSubSector.setTextColor(root.context.requiredColor(UiKitAttrs.colorTertiary_400))
                    }

                    if (subSector.commodities.isNotEmpty()) {
                        tvText.text = context.getString(R.string.label_change)
                    } else {
                        tvText.text = context.getString(R.string.label_choose)
                    }
                    cgCommodities.addAll(
                        subSector.commodities.map {
                            it.name
                        }
                    )
                }
            }
        }

        private fun cardViewStyling(primary: Int, secondary: Int, button: Int) {
            with(bindLayout) {
                cvSubsector.setCardBackgroundColor(
                    primary
                )
                btnAction.setCardBackgroundColor(
                    button
                )
                cgCommodities.chipType = ChipsType.DEFAULT_TYPE
                cgCommodities.setChipBackgroundColor(primary)
                cgCommodities.setChipStrokeColor(secondary)
                cgCommodities.setTextColors(secondary)
            }
        }
    }
}
