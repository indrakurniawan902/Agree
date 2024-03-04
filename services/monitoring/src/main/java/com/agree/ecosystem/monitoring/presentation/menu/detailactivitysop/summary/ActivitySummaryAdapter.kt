package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.summary

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import com.agree.ecosystem.monitoring.databinding.ItemActivitySummaryBinding
import com.agree.ui.UiKitDimens
import com.agree.ui.data.reqres.model.FormItem
import com.agree.ui.utils.enums.FormType
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.loadImage

class ActivitySummaryAdapter : DevRecyclerViewAdapter<FormItem>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<FormItem> =
        ViewHolder(ItemActivitySummaryBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemActivitySummaryBinding) :
        DevItemViewHolder<FormItem>(bindLayout) {
        override fun bind(data: FormItem?) {
            with(bindLayout) {
                data?.let {
                    tvLabel.text = it.label
                    tvValue.text = when {
                        it.value.isEmpty() -> {
                            "-"
                        }
                        it.prefix.isNotEmpty() && it.suffix.isNotEmpty() -> {
                            "${it.prefix} ${it.value} ${it.suffix}"
                        }
                        it.prefix.isNotEmpty() -> {
                            "${it.prefix} ${it.value}"
                        }
                        it.suffix.isNotEmpty() -> {
                            "${it.value} ${it.suffix}"
                        }
                        else -> {
                            it.value
                        }
                    }
                    if (it.componentType == FormType.FORM_COMP_TYPE_PHOTO_UPLOAD.type && it.value.isNotEmpty()) {
                        tvValue.gone()
                        it.value.split(",").toList().forEach { url ->
                            val img = ImageView(root.context).apply {
                                layoutParams = LinearLayout.LayoutParams(
                                    root.context.resources.getDimension(UiKitDimens.dimen_48dp).toInt(),
                                    root.context.resources.getDimension(UiKitDimens.dimen_48dp).toInt()
                                ).apply {
                                    topMargin = root.context.resources.getDimension(UiKitDimens.dimen_4dp).toInt()
                                }
                                loadImage(
                                    root.context,
                                    imageUrl = url,
                                    scaleType = ImageView.ScaleType.CENTER_INSIDE
                                )
                            }
                            root.addView(img)
                        }
                    }
                }
            }
        }
    }
}
