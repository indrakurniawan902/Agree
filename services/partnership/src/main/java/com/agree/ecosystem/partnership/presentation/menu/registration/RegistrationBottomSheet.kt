package com.agree.ecosystem.partnership.presentation.menu.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.LayoutRegistrationSuccessBinding
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding

class RegistrationBottomSheet(private val from: String) : BottomSheetDialogFragment() {

    private val binding: LayoutRegistrationSuccessBinding by viewBinding()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            when (from) {
                "EmptyCommodity" -> {
                    tvTitle.text = getString(R.string.label_choose_commodities_first)
                    tvDescription.text = getString(R.string.label_must_choose_least_one_commodities)
                    btnNext.text = getString(R.string.label_understand)
                }
                "Registered" -> {
                    tvTitle.text = getString(R.string.label_cannot_register)
                    tvDescription.text = getString(R.string.label_already_registered)
                    btnNext.text = getString(R.string.label_understand)
                }
                else -> {
                    Glide.with(requireActivity()).load(R.drawable.ic_gabung_kemitraan).into(ivIllus)
                }
            }
            btnNext.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "RegistrationSuccessBottomSheet"
    }
}
