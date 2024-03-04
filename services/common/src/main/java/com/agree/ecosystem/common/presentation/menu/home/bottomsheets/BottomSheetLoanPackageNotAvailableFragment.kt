package com.agree.ecosystem.common.presentation.menu.home.bottomsheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.common.databinding.FragmentBottomSheetLoanPackageNotEvailableBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding

class BottomSheetLoanPackageNotAvailableFragment : BottomSheetDialogFragment() {
    private val binding: FragmentBottomSheetLoanPackageNotEvailableBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}
