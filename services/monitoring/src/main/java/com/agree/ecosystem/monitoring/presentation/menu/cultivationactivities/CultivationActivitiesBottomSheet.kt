package com.agree.ecosystem.monitoring.presentation.menu.cultivationactivities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.monitoring.databinding.FragmentCultivitionActivitiesDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding

class CultivationActivitiesBottomSheet() : BottomSheetDialogFragment() {

    private val binding: FragmentCultivitionActivitiesDialogBinding by viewBinding()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
    }

    private fun initAction() {
        binding.btnPositive.setOnClickListener { dismiss() }
    }

    companion object {
        const val TAG = "PhaseActivityDialog"
    }
}
