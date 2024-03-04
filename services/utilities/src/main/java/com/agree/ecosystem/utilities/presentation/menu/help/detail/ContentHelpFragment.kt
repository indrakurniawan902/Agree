package com.agree.ecosystem.utilities.presentation.menu.help.detail

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.utilities.databinding.FragmentContentHelpBinding
import com.agree.ecosystem.utilities.domain.reqres.model.help.Question

class ContentHelpFragment(
    private val data: List<Question>
) : AgrFragment<FragmentContentHelpBinding>() {

    override var isTrackAnalytics: Boolean = false

    override fun initUI() {
        super.initUI()
        with(binding) {
            root.adapter = DetailQuestionAdapter().apply {
                addAll(data)
            }
        }
    }
}
