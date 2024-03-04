package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities.dateinput

import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.ItemMonthBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.MonthItem
import com.agree.ui.R
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getColorResource
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class SelectMonthAdapter(
    private val onItemClicked: (month: Int) -> Unit
) : DevRecyclerViewAdapter<MonthItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<MonthItem> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemMonthBinding.inflate(getLayoutInflater(parent), parent, false)
            )
            else -> DevEndlessItemViewHolder(
                ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                getLoadMoreListener(),
                isLoading,
                loadMoreSkip,
                loadMoreLimit
            )
        }

    override fun getItemViewType(position: Int): Int =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemMonthBinding) :
        DevItemViewHolder<MonthItem>(bindLayout) {

        override fun bind(data: MonthItem?) {
            with(bindLayout) {

                data?.let { value ->
                    val localDate = LocalDate.now().withMonth(value.month)
                    tvMonth.text = SimpleDateFormat("MMM", Locale("id"))
                        .format(Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant()))

                    if (value.selected) {
                        tvMonth.setBackgroundColor(getColorResource(R.color.information_500))
                        tvMonth.setTextColor(getColorResource(R.color.white))
                    } else {
                        tvMonth.setBackgroundColor(getColorResource(R.color.white))
                        tvMonth.setTextColor(getColorResource(R.color.information_500))
                    }
                    root.setOnClickListener {
                        onItemClicked(value.month)
                    }
                }
            }
        }
    }
}
