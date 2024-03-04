package com.agree.ecosystem.utilities.presentation.menu.help.detail

import android.view.ViewGroup
import com.agree.ecosystem.utilities.databinding.ItemHelpQuestionBinding
import com.agree.ecosystem.utilities.domain.reqres.model.help.Question
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding

class DetailQuestionAdapter : DevRecyclerViewAdapter<Question>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Question> {
        return ViewHolder(inflateBinding(parent))
    }

    inner class ViewHolder(private val bindingLayout: ItemHelpQuestionBinding) :
        DevItemViewHolder<Question>(bindingLayout) {
        override fun bind(data: Question?) {
            with(bindingLayout) {
                data?.let {
                    containerQuestion.title = it.question
                    containerQuestion.showSeparator = true

                    rvAnswer.apply {
                        adapter = DetailAnswerAdapter().apply {
                            addAll(it.data)
                        }
                    }
                }
            }
        }
    }
}
