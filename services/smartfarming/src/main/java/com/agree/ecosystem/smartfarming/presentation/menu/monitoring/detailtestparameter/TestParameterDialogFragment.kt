package com.agree.ecosystem.smartfarming.presentation.menu.monitoring.detailtestparameter

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.base.AgrBottomSheet
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.smartfarming.R
import com.agree.ecosystem.smartfarming.databinding.FragmentTestParameterDialogBinding
import com.agree.ecosystem.smartfarming.domain.reqres.model.SmartFarming.ParameterTestFormSchema
import com.agree.ui.UiKitAttrs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.devbase.utils.util.getStringResource
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding


class TestParameterDialogFragment(
    private val data: ParameterTestFormSchema
) : AgrBottomSheet() {

    private val adapter: DataParameterAdapter by lazy {
        DataParameterAdapter()
    }

    private val adapterRecommendation: RecommendationAdapter by lazy {
        RecommendationAdapter(data)
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            ivClose.setOnClickListener {
                dismiss()
            }
            tvHeading.text = data.label
            Glide.with(root.context)
                .asBitmap()
                .load(data.icon)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        ivTemp.bitmap = resource
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })
            tvTemp.text = data.value
            when (data.status) {
                ParameterTestFormSchema.Status.GOOD -> {
                    tvStatus.apply {
                        text = getStringResource(R.string.good)
                        setTextColor(UiKitAttrs.success_normal.getAttrsValue(context))
                    }
                }

                ParameterTestFormSchema.Status.WARY -> {
                    tvStatus.apply {
                        text = getStringResource(R.string.wary)
                        setTextColor(UiKitAttrs.warning_normal.getAttrsValue(context))
                    }
                }

                ParameterTestFormSchema.Status.BAD -> {
                    tvStatus.apply {
                        text = getStringResource(R.string.bad)
                        setTextColor(UiKitAttrs.error_normal.getAttrsValue(context))
                    }
                }
            }


            rvRecommendation.apply {
                adapter = this@TestParameterDialogFragment.adapterRecommendation.apply {
                    addAll(data.status_recommendation)
                }
            }

            rvListStatus.apply {
                adapter = this@TestParameterDialogFragment.adapter.apply {
                    addAll(data.smartfarm_reference?.reference)
                }
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

    private val binding: FragmentTestParameterDialogBinding by viewBinding()
}