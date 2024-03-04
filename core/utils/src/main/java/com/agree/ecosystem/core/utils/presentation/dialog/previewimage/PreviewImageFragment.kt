package com.agree.ecosystem.core.utils.presentation.dialog.previewimage

import android.content.res.Resources
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.databinding.LayoutPreviewImageBinding
import com.devbase.utils.ext.gone
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding

class PreviewImageFragment(
    private val images: List<String>,
    private val position: Int
) : BottomSheetDialogFragment() {
    private val binding: LayoutPreviewImageBinding by viewBinding()

    var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    private var isToolbarEnd = false
    private var colorToolbar: Int = 0
    private var colorBackground: Int = 0
    private var toolbarCloseTitle = "Close"
    private var isToolbarCloseVisible = true
    private var isIndicatorVisible = true

    fun setToolbarCloseGravityEnd(): PreviewImageFragment {
        isToolbarEnd = true
        return this
    }

    fun setToolbarCloseColor(@ColorRes colorId: Int): PreviewImageFragment {
        this.colorToolbar = colorId
        return this
    }

    fun setBackgroundColor(@ColorRes colorId: Int): PreviewImageFragment {
        this.colorBackground = colorId
        return this
    }

    fun setToolbarCloseTitle(title: String): PreviewImageFragment {
        this.toolbarCloseTitle = title
        return this
    }

    fun setToolbarCloseGone(): PreviewImageFragment {
        this.isToolbarCloseVisible = false
        return this
    }

    fun setIndicatorGone(): PreviewImageFragment {
        this.isIndicatorVisible = false
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.root.viewTreeObserver.addOnGlobalLayoutListener {
            val dialog = dialog as BottomSheetDialog?
            val bottomSheet: FrameLayout? =
                dialog!!.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet!!)
            val height: Int = binding.root.height
            bottomSheetBehavior?.peekHeight = height
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
            colorBackground = if (colorBackground == 0) Color.BLACK
            else ContextCompat.getColor(requireContext(), colorBackground)
            root.setBackgroundColor(colorBackground)
            toolbar.setBackgroundColor(colorBackground)
            initToolbar()

            if (isToolbarCloseVisible) {
                tvTitle.text = toolbarCloseTitle
                containerToolbar.setOnClickListener {
                    dismiss()
                }
            } else containerToolbar.gone()

            vpPhoto.apply {
                adapter = previewImageAdapter.apply {
                    clear()
                    addAll(images)
                    onFitScreen {
                        bottomSheetBehavior?.isDraggable = it
                        isUserInputEnabled = it
                    }
                }
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3
                getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
                setCurrentItem(position, false)
            }
            if (isIndicatorVisible) dotsIndicator.setViewPager2(vpPhoto)
            else dotsIndicator.gone()
        }
    }

    private val previewImageAdapter: PreviewImageAdapter by lazy {
        PreviewImageAdapter(
            requireContext()
        )
    }

    private fun initToolbar() {
        with(binding) {
            val margin = 45
            if (isToolbarEnd) {
                val csImage = ConstraintSet()
                csImage.clone(containerToolbar)
                csImage.connect(
                    imgClose.id,
                    ConstraintSet.END,
                    containerToolbar.id,
                    ConstraintSet.END,
                    0
                )
                csImage.applyTo(containerToolbar)
                val csTitle = ConstraintSet()
                csTitle.clone(containerToolbar)
                csTitle.connect(
                    tvTitle.id,
                    ConstraintSet.END,
                    imgClose.id,
                    ConstraintSet.START,
                    margin
                )
                csTitle.applyTo(containerToolbar)

                val layoutParams = imgClose.layoutParams as ConstraintLayout.LayoutParams
                layoutParams.marginEnd = margin
                layoutParams.rightMargin = margin
                imgClose.layoutParams = layoutParams
            } else {
                val csImage = ConstraintSet()
                csImage.clone(containerToolbar)
                csImage.connect(
                    imgClose.id,
                    ConstraintSet.START,
                    containerToolbar.id,
                    ConstraintSet.START,
                    0
                )
                csImage.applyTo(containerToolbar)
                val csTitle = ConstraintSet()
                csTitle.clone(containerToolbar)
                csTitle.connect(
                    tvTitle.id,
                    ConstraintSet.START,
                    imgClose.id,
                    ConstraintSet.END,
                    margin
                )
                csTitle.applyTo(containerToolbar)
            }
            colorToolbar = if (colorToolbar == 0) Color.WHITE
            else ContextCompat.getColor(requireContext(), colorToolbar)

            tvTitle.setTextColor(colorToolbar)
            imgClose.setColorFilter(colorToolbar, PorterDuff.Mode.SRC_IN)
        }
    }

    companion object {
        const val TAG = "PreviewImageDialog"
    }
}
