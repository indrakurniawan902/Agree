package com.agree.ecosystem.finances.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.agree.ecosystem.finances.databinding.ItemCustomInfoLayoutBinding
import com.oratakashi.viewbinding.core.tools.gone

class CustomInfoProfileCultivatorFinance(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        ItemCustomInfoLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    fun bindLeft(title: String, value: String = "-", message: String = "") {
        binding.apply {
            if (message == "") icLeft.gone()
            tvTitleLeft.text = title
            tvValueTitleLeft.text = value
        }
    }

    fun bindRight(title: String, value: String = "-", message: String = "") {
        with(binding) {
            if (message == "") icRight.gone()
            tvTitleRight.text = title
            tvValueTitleRight.text = value
        }
    }
}
