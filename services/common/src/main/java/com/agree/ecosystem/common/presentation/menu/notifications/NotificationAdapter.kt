package com.agree.ecosystem.common.presentation.menu.notifications

import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.ItemNotificationBinding
import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox
import com.agree.ecosystem.common.utils.convertTimeToListNotif
import com.agree.ecosystem.common.utils.enums.notification.CategoriesInbox
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.devbase.presentation.databinding.ItemLoadMoreBinding
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevEndlessItemViewHolder
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getDrawableResource

class NotificationAdapter(val onClick: (data: Inbox?) -> Unit) :
    DevRecyclerViewAdapter<Inbox>(onClickListener = onClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Inbox> =
        when (viewType) {
            CONTENT -> ViewHolder(
                ItemNotificationBinding.inflate(getLayoutInflater(parent), parent, false)
            )

            else -> DevEndlessItemViewHolder(
                ItemLoadMoreBinding.inflate(getLayoutInflater(parent)),
                getLoadMoreListener(),
                isLoading, loadMoreSkip, loadMoreLimit
            )
        }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    inner class ViewHolder(private val bindLayout: ItemNotificationBinding) :
        DevItemViewHolder<Inbox>(bindLayout) {
        override fun bind(data: Inbox?) {
            fun setIconListNotification(background: Int) {
                with(bindLayout) {
//                    ivIcon.resDrawable = getDrawableResource(background)
                    ivIcon.setImageResource(background)
                }
            }
            with(bindLayout) {
                data?.apply {
                    tvTitle.text = this.category
                    tvDate.text =
                        this.createdAt.convertTimeToListNotif(ConverterDate.SIMPLE_DAY_MONTH)
                    tvSubTitle.text = this.title

                    when (this.category) {
                        CategoriesInbox.PARTNERSHIP.value -> {
                            setIconListNotification(R.drawable.ic_background_circle_kemitraan)
                            tvDesc.text = this.description.replace("\n\n", "\n")
                        }

                        CategoriesInbox.MONITORING.value -> {
                            val desc = HtmlCompat.fromHtml(
                                this.description,
                                HtmlCompat.FROM_HTML_MODE_COMPACT
                            ).toString().replace("\\r\\n\\r\\n", "").replace("\n\n", "\n")
                            tvDesc.text = desc
                            setIconListNotification(R.drawable.ic_background_circle_monitoring)
                        }
                    }

                    if (!isRead) {
                        root.background = getDrawableResource(com.agree.ui.R.color.primary_100)
                    } else {
                        root.background = getDrawableResource(com.agree.ui.R.color.white)
                    }
                }
            }
        }
    }
}
