package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.agree.ecosystem.core.utils.presentation.dialog.previewimage.PreviewImageFragment
import com.agree.ecosystem.core.utils.utility.*
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.ItemIncidentCommentBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.incidentcomment.IncidentComment
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.PhotoAdapter
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder

class IncidentReportAdapter(
    val activity: FragmentActivity
) : DevRecyclerViewAdapter<IncidentComment>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<IncidentComment> =
        ViewHolder(ItemIncidentCommentBinding.inflate(getLayoutInflater(parent), parent, false))

    inner class ViewHolder(private val bindLayout: ItemIncidentCommentBinding) :
        DevItemViewHolder<IncidentComment>(bindLayout) {
        override fun bind(data: IncidentComment?) {
            with(bindLayout) {
                data?.let { incidentComment ->
                    val getGravity =
                        if (incidentComment.commenterType.lowercase() == "user") Gravity.END else Gravity.START

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        gravity = getGravity
                    }
                    tvFrom.layoutParams = params
                    tvDateTime.layoutParams = params
                    rvImages.layoutParams = params

                    val textColor = when (incidentComment.commenterType.lowercase()) {
                        "company" -> com.agree.ui.R.color.tertiary_900
                        "company-member" -> com.agree.ui.R.color.tertiary_900
                        "agree" -> com.agree.ui.R.color.primary_500
                        else -> com.agree.ui.R.color.tertiary_900
                    }

                    tvFrom.text = incidentComment.commenterName
                    tvFrom.setTextColor(ContextCompat.getColor(activity, textColor))

                    val dateTime = if (absoluteAdapterPosition == 0)
                        incidentComment.createdAt.convertUTCTimezone(ConverterDate.SHORT_DATE, visibleTimeZone = false)
                    else incidentComment.createdAt.convertUTCTimezone(ConverterDate.SIMPLE_DATE_TIME_DOT)

                    tvDateTime.text = dateTime
                    val adapter = PhotoAdapter(true) {
                        PreviewImageFragment(incidentComment.images, it)
                            .setBackgroundColor(com.agree.ui.R.color.tertiary_700)
                            .setToolbarCloseTitle(activity.resources.getString(R.string.label_close))
                            .show(
                                activity.supportFragmentManager,
                                PreviewImageFragment.TAG
                            )
                    }
                    adapter.addOrUpdateAll(incidentComment.images)
                    rvImages.adapter = adapter
                    rvImages.scrollToPosition(incidentComment.images.size - 1)
                    rvImages.visibility =
                        if (incidentComment.images.isEmpty()) View.GONE else View.VISIBLE
                    with(wvContent) {
                        scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
                        val align =
                            if (incidentComment.commenterType.lowercase() == "user") "right" else "left"
                        val htmlString = "<style>"
                            .plus("@font-face {font-family: 'opensans_regular';")
                            .plus("src: url('file:///android_res/font/opensans_regular.ttf');}")
                            .plus("p{")
                            .plus("text-align:$align;")
                            .plus("font-family: 'opensans_regular';")
                            .plus("font-size: 14px;")
                            .plus("font-weight: 400;")
                            .plus("line-height: 21px;")
                            .plus("color:#616161;")
                            .plus("}")
                            .plus("</style>")
                            .plus("<p>${incidentComment.message}</p>")
                        loadDataWithBaseURL(
                            "file:///android_res/",
                            htmlString,
                            "text/html",
                            "UTF-8",
                            null
                        )
                    }
                }
            }
        }
    }
}
