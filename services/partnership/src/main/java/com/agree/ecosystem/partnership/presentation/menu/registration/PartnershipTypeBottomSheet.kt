package com.agree.ecosystem.partnership.presentation.menu.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.partnership.databinding.LayoutPartnershipTypeBottomSheetBinding
import com.agree.ecosystem.partnership.domain.reqres.model.company.PartnerType
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.bottomsheet.viewBinding

class PartnershipTypeBottomSheet(private val data: List<PartnerType>) :
    BottomSheetDialogFragment() {

    private val binding: LayoutPartnershipTypeBottomSheetBinding by viewBinding()

    private val partnershipTypeAdapter: PartnershipTypeAdapter by lazy {
        PartnershipTypeAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initAction()
    }

    private fun initUI() {
        with(binding) {
            rvPartnership.adapter = partnershipTypeAdapter
        }
    }

    private fun initData() {
        partnershipTypeAdapter.apply {
            setPartnershipType(data)
            addOrUpdateAll(data)
        }
    }

    private fun initAction() {
        with(binding) {
            btnClose.setOnClickListener {
                dismiss()
            }
        }
    }

    companion object {
        const val TAG = "PartnershipTypeBottomSheet"
    }
}
