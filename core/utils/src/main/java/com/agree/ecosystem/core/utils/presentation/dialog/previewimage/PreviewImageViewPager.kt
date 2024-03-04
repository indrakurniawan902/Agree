package com.agree.ecosystem.core.utils.presentation.dialog.previewimage

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class PreviewImageViewPager : ViewPager {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    override fun canScroll(v: View, checkV: Boolean, dx: Int, x: Int, y: Int): Boolean {
        return if (v is PreviewImageView) {
            v.canScrollHorizontallyFroyo(-dx)
        } else {
            super.canScroll(v, checkV, dx, x, y)
        }
    }
}
