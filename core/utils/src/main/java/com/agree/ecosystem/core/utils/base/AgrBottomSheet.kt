package com.agree.ecosystem.core.utils.base

import android.os.Bundle
import android.view.View
import com.agree.ecosystem.core.analytics.AgrAnalytics
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

abstract class AgrBottomSheet : BottomSheetDialogFragment() {

    open val analytics by inject<AgrAnalytics>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initData()
        initUI()
        initAction()
    }

    open fun initData() {
        // Do Nothing
    }
    open fun initUI() {
        // Do Nothing
    }
    open fun initAction() {
        // Do Nothing
    }
    open fun initObserver() {
        // Do Nothing
    }
}
