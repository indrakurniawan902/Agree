package com.agree.ecosystem.core.utils.widget.finance

import android.content.res.ColorStateList
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.databinding.ItemStepperBinding
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getColorResource
import com.oratakashi.viewbinding.core.tools.gone

class StepperAdapter(var progressPosition: Int, onClick: (String?) -> Unit) :
    DevRecyclerViewAdapter<String>(onClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<String> {
        return when (viewType) {
            CONTENT -> ViewHolder(
                ItemStepperBinding.inflate(getLayoutInflater(parent), parent, false)
            )

            else -> {
                DevEndlessItemViewHolder(
                    ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                    getLoadMoreListener(),
                    isLoading,
                    loadMoreSkip,
                    loadMoreLimit
                )
            }
        }
    }

    inner class ViewHolder(private val bindLayout: ItemStepperBinding) :
        DevItemViewHolder<String>(bindLayout) {
        override fun bind(data: String?) {
            with(bindLayout) {
                data?.let {
                    tvStepperNumber.text = StringBuilder("${adapterPosition + 1}")
                    tvStepperName.text = it

                    if (adapterPosition < progressPosition) {
                        tvStepperName.isActivated = false
                        tvStepperNumber.isActivated = true
                        tvStepperNumber.text = ""
                    }

                    if (adapterPosition == progressPosition) {
                        tvStepperNumber.backgroundTintList =
                            ColorStateList.valueOf(getColorResource(com.agree.ui.R.color.white))
                        tvStepperName.isActivated = true
                        tvStepperNumber.isActivated = false
                        viewHorizontalLine.isActivated = true
                    }

                    if (adapterPosition == listData.size - 1) {
                        viewHorizontalLine.gone()
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    fun setProgress(progress: Int) {
        progressPosition = progress
    }
}
