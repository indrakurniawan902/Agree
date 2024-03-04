package com.agree.ecosystem.utilities.presentation.menu.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.utilities.databinding.FragmentMessageDialogBinding
import com.devbase.utils.ext.gone
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding

class DialogMessageFragment(val title: String? = null, val message: String? = null) :
    AgrBottomSheet() {

    override fun initUI() {
        super.initUI()
        with(binding) {
            if (title != null) tvTitle.text = title
            else tvTitle.gone()
            if (message != null) tvMessage.text = message
            else tvMessage.gone()

            if (mPositiveAction != null) {
                btnPositive.text = mPositiveAction?.first.orEmpty()
                btnPositive.setOnClickListener {
                    mPositiveAction?.second?.invoke()
                    dismiss()
                }
            } else btnPositive.gone()

            if (mNegativeAction != null) {
                btnNegative.text = mNegativeAction?.first.orEmpty()
                btnNegative.setOnClickListener {
                    mNegativeAction?.second?.invoke()
                    dismiss()
                }
            } else btnNegative.gone()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    fun setPositiveButton(positiveAction: Pair<String, (() -> Unit)?>? = null) {
        mPositiveAction = positiveAction
    }

    fun setNegativeButton(negativeAction: Pair<String, (() -> Unit)?>? = null) {
        mNegativeAction = negativeAction
    }

    private val binding: FragmentMessageDialogBinding by viewBinding()
    private var mPositiveAction: Pair<String, (() -> Unit)?>? = null
    private var mNegativeAction: Pair<String, (() -> Unit)?>? = null
}
