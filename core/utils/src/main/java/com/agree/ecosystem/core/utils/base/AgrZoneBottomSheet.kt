package com.agree.ecosystem.core.utils.base

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

abstract class AgrZoneBottomSheet : AgrBottomSheet() {
    override fun onStart() {
        super.onStart()
        runCatching {
            val sheetDialog = dialog as BottomSheetDialog
            val bottomSheet = sheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            bottomSheet.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }
}
