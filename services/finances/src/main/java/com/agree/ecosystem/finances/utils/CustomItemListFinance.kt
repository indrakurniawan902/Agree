package com.agree.ecosystem.finances.utils

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.agree.ecosystem.finances.R
import com.agree.ecosystem.finances.databinding.ItemLayoutCustomListFinanceBinding
import com.oratakashi.viewbinding.core.tools.gone

class CustomItemListFinance(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = ItemLayoutCustomListFinanceBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    fun bindData(
        message: String, desc: String, showIconRight: Boolean = false,
        statusComplete: Boolean = true
    ) {
        with(binding) {
            tvTitleErrorMessage.text = message
            tvDescErrorMessage.text = desc
            if (showIconRight == false) ivRight.gone()
            if (statusComplete == false) ivIcon.loadImage(R.drawable.ic_x_circle) //ivIcon.loadImage(R.drawable.ic_check)
        }
    }
}
