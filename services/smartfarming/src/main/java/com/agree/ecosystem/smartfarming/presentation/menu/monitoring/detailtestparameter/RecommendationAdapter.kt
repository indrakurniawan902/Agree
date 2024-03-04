package com.agree.ecosystem.smartfarming.presentation.menu.monitoring.detailtestparameter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.ViewGroup
import com.agree.ecosystem.smartfarming.databinding.ItemRecommendationBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestFormSchema
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.RecommendationItem
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding
import com.telkom.legion.component.alert.LgnAlert

class RecommendationAdapter(
    private val status: ParameterTestFormSchema
) : DevRecyclerViewAdapter<RecommendationItem>() {

    inner class ViewHolder(private val bindLayout: ItemRecommendationBinding) :
        DevItemViewHolder<RecommendationItem>(bindLayout) {
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        override fun bind(data: RecommendationItem?) {
            with(bindLayout) {
                data?.let { recommendation ->

                    when (status.status) {
                        ParameterTestFormSchema.Status.GOOD -> {
                            laRecommendation.type = LgnAlert.AlertType.SUCCESS
                        }

                        ParameterTestFormSchema.Status.WARY -> {
                            laRecommendation.type = LgnAlert.AlertType.WARNING
                        }

                        ParameterTestFormSchema.Status.BAD -> {
                            laRecommendation.type = LgnAlert.AlertType.ERROR
                        }
                    }

                    val content = Html.fromHtml(recommendation.content)
                    laRecommendation.apply {
                        description = content.toString().trim()
                        actionText = recommendation.actionLabel
                        setOnActionClickListener {
                            val i = Intent(Intent.ACTION_VIEW)
                            i.data = Uri.parse(recommendation.link)
                            root.context.startActivity(i)
                        }
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DevItemViewHolder<RecommendationItem> {
        return ViewHolder(inflateBinding(parent))
    }
}