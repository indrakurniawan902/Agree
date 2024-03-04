package com.agree.ecosystem.common.presentation.menu.home.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.common.databinding.FragmentBottomSheetConnectionBinding
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding

class BottomSheetConnectionFragment : AgrBottomSheet() {

    override fun initUI() {
        super.initUI()
        with(binding) {
            btnTryOffline.setOnClickListener {
                dismiss()
            }
            btnTryOnline.setOnClickListener {
                dismiss()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    private val binding: FragmentBottomSheetConnectionBinding by viewBinding()
}
