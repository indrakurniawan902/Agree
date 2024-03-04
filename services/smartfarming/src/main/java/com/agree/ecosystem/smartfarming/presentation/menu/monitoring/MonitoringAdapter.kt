package com.agree.ecosystem.smartfarming.presentation.menu.monitoring
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.smartfarming.R
import com.agree.ecosystem.smartfarming.databinding.ItemTestParameterBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestFormSchema
import com.agree.ui.UiKitAttrs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.util.getStringResource
import timber.log.Timber

class MonitoringAdapter(
    onclick: (parameterTest: ParameterTestFormSchema?) -> Unit
) : DevRecyclerViewAdapter<ParameterTestFormSchema>(onClickListener = onclick){
    private var unreadCount = 0

    inner class ViewHolder(private val bindLayout: ItemTestParameterBinding) :
        DevItemViewHolder<ParameterTestFormSchema>(bindLayout) {
        override fun bind(data: ParameterTestFormSchema?) {
            with(bindLayout) {
                data?.let { parameterData ->
                    val context = root.context
                    tvTestParameterTitle.text = parameterData.label
                    tvTestParameterDataOfUnitNumber.text = parameterData.value
                    Glide.with(root.context)
                        .asBitmap()
                        .load(parameterData.icon)
                        .into(object : CustomTarget<Bitmap>() {
                            override fun onResourceReady(
                                resource: Bitmap,
                                transition: Transition<in Bitmap>?
                            ) {
                                ivIconParameter.bitmap = resource
                            }

                            override fun onLoadCleared(placeholder: Drawable?) {}
                        })

                    /*
                    * Check if parameter is not read and show alert icon
                    * */
                    if (parameterData.isRead) {
                        tvTestParameterTitle.setCompoundDrawablesWithIntrinsicBounds(
                            null,
                            null,
                            null,
                            null
                        )
                    } else {
                        tvTestParameterTitle.setCompoundDrawablesWithIntrinsicBounds(
                            0,
                            0,
                            R.drawable.ic_notification_alert,
                            0
                        )
                    }

                    when (parameterData.status) {
                        ParameterTestFormSchema.Status.GOOD -> {
                            tvTestParameterStatus.apply {
                                text = getStringResource(R.string.good)
                                setTextColor(UiKitAttrs.success_normal.getAttrsValue(context))
                            }
                            cvTestParameterStatus.strokeColor =
                                UiKitAttrs.success_normal.getAttrsValue(context)
                        }

                        ParameterTestFormSchema.Status.WARY -> {
                            tvTestParameterStatus.apply {
                                text = getStringResource(R.string.wary)
                                setTextColor(UiKitAttrs.warning_normal.getAttrsValue(context))
                            }
                            cvTestParameterStatus.strokeColor =
                                UiKitAttrs.warning_normal.getAttrsValue(context)
                        }

                        ParameterTestFormSchema.Status.BAD -> {
                            tvTestParameterStatus.apply {
                                text = getStringResource(R.string.bad)
                                setTextColor(UiKitAttrs.error_normal.getAttrsValue(context))
                            }
                            cvTestParameterStatus.strokeColor =
                                UiKitAttrs.error_normal.getAttrsValue(context)
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int) =
        if (listData[position] != null) CONTENT else LOAD_MORE

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<ParameterTestFormSchema> {
        return ViewHolder(ItemTestParameterBinding.inflate(getLayoutInflater(parent), parent, false))
    }

    fun calculateUnreadCount() {
        unreadCount = 0
        for (data in listData) {
            if (data != null && !data.isRead) {
                unreadCount++
            }
        }
    }

    fun getUnreadCount(): Int{
        return unreadCount
    }
}
