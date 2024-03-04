package com.agree.ecosystem.utilities.presentation.menu.help.detail

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.agree.ecosystem.utilities.databinding.ItemHelpAnswerBinding
import com.agree.ecosystem.utilities.domain.reqres.model.help.Answer
import com.devbase.presentation.recyclerview.DevRecyclerViewAdapter
import com.devbase.presentation.recyclerview.viewbinding.DevItemViewHolder
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.visible
import com.oratakashi.viewbinding.core.binding.recyclerview.inflateBinding
import com.oratakashi.viewbinding.core.image.load

class DetailAnswerAdapter : DevRecyclerViewAdapter<Answer>() {

    private var position = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevItemViewHolder<Answer> {
        return ViewHolder(inflateBinding(parent))
    }

    private inner class ViewHolder(
        private val bindLayout: ItemHelpAnswerBinding
    ) : DevItemViewHolder<Answer>(bindLayout) {
        @SuppressLint("SetTextI18n")
        override fun bind(data: Answer?) {
            with(bindLayout) {
                data?.let {
                    if (it.type == "numbering") {
                        tvNumbering.visible()
                        tvNumbering.text = "${this@DetailAnswerAdapter.position++}."
                    } else {
                        tvNumbering.gone()
                    }

                    tvAnswer.text = it.desc

                    if (it.image.isNotEmpty()) {
                        ivImage.apply {
                            visible()
                            load(it.image)
                        }
                    }
                }
            }
        }
    }
}
